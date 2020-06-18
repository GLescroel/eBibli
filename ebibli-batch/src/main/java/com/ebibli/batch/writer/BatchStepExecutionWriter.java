package com.ebibli.batch.writer;

import com.ebibli.batch.config.BiblioJobProperties;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchStepExecutionWriter extends FlatFileItemWriter<StepExecution> {

    private BiblioJobProperties biblioJobProperties;

    private String reportFileName;
    private String reportHeader;
    private String readerText;
    private String processorFilterText;
    private String processorSkipText;
    private String writerText;


    public BatchStepExecutionWriter(BiblioJobProperties biblioJobProperties,
                                    String reportFileName,
                                    String reportHeader,
                                    String readerText,
                                    String processorFilterText,
                                    String processorSkipText,
                                    String writerText) {
        this.biblioJobProperties = biblioJobProperties;
        this.reportFileName = reportFileName;
        this.reportHeader = reportHeader;
        this.readerText = readerText;
        this.processorFilterText = processorFilterText;
        this.processorSkipText = processorSkipText;
        this.writerText = writerText;
    }

    public BatchStepExecutionWriter initialize() {
        final BatchStepExecutionWriter writer = new BatchStepExecutionWriter(biblioJobProperties,
                reportFileName, reportHeader, readerText, processorFilterText, processorSkipText, writerText);
        writer.setLineAggregator(item -> new StringBuilder()
                .append("Statut: ")
                .append(item.getStatus()).append(System.lineSeparator())
                .append("Date de début: ")
                .append(item.getStartTime()).append(System.lineSeparator())
                .append("Date de fin: ")
                .append(item.getEndTime()).append(System.lineSeparator())
                .append(readerText)
                .append(item.getReadCount()).append(System.lineSeparator())
                .append(processorFilterText)
                .append(item.getFilterCount()).append(System.lineSeparator())
                .append(processorSkipText)
                .append(item.getProcessSkipCount()).append(System.lineSeparator())
                .append(writerText)
                .append(item.getWriteCount()).append(System.lineSeparator())
                .append("Erreurs: ")
                .append(item.getFailureExceptions()).append(System.lineSeparator())
                .toString());
        writer.setAppendAllowed(true);
        return writer;
    }

    public void writeHeader() {
        Path path = Paths.get(biblioJobProperties.getReportPath(),
                String.format(reportFileName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        this.setResource(new FileSystemResource(path.toString()));
        this.setHeaderCallback(headerWriter -> headerWriter.append(reportHeader));
        ExecutionContext executionContext = new ExecutionContext();
        this.open(executionContext);
    }
}
