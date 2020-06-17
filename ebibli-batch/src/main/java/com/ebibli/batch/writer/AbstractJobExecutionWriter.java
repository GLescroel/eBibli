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

public abstract class AbstractJobExecutionWriter extends FlatFileItemWriter<StepExecution> {

    private BiblioJobProperties biblioJobProperties;

    public AbstractJobExecutionWriter(BiblioJobProperties biblioJobProperties) {
        this.biblioJobProperties = biblioJobProperties;
    }

    public AbstractJobExecutionWriter initialize() {
        final AbstractJobExecutionWriter writer = new ReminderJobExecutionWriter(biblioJobProperties);
        writer.setLineAggregator(item -> new StringBuilder()
                .append("Statut: ")
                .append(item.getStatus()).append(System.lineSeparator())
                .append("Date de début: ")
                .append(item.getStartTime()).append(System.lineSeparator())
                .append("Date de fin: ")
                .append(item.getEndTime()).append(System.lineSeparator())
                .append("Nombre de valeurs luse: ")
                .append(item.getReadCount()).append(System.lineSeparator())
                .append("Nombre de valeurs filtrées : ")
                .append(item.getFilterCount()).append(System.lineSeparator())
                .append("Nombre de valeurs rejetés: ")
                .append(item.getProcessSkipCount()).append(System.lineSeparator())
                .append("Nombre de valeurs écrites : ")
                .append(item.getWriteCount()).append(System.lineSeparator())
                .append("Erreurs: ")
                .append(item.getFailureExceptions()).append(System.lineSeparator())
                .toString());
        writer.setAppendAllowed(true);
        return writer;
    }

    public void writeHeader() {
        Path path = Paths.get(biblioJobProperties.getReportPath(),
                String.format("batch-job-execution-%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        this.setResource(new FileSystemResource(path.toString()));
        this.setHeaderCallback(headerWriter -> headerWriter.append("--- Rapport d'execution du Batch ---"));
        ExecutionContext executionContext = new ExecutionContext();
        this.open(executionContext);
    }
}
