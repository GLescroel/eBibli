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

public class ReminderJobExecutionWriter extends FlatFileItemWriter<StepExecution> {

    private BiblioJobProperties biblioJobProperties;

    public ReminderJobExecutionWriter(BiblioJobProperties biblioJobProperties) {
        this.biblioJobProperties = biblioJobProperties;
    }

    public ReminderJobExecutionWriter initialize() {
        final ReminderJobExecutionWriter writer = new ReminderJobExecutionWriter(this.biblioJobProperties);
        writer.setLineAggregator(item -> new StringBuilder()
                .append("Statut: ")
                .append(item.getStatus()).append(System.lineSeparator())
                .append("Date de début: ")
                .append(item.getStartTime()).append(System.lineSeparator())
                .append("Date de fin: ")
                .append(item.getEndTime()).append(System.lineSeparator())
                .append("Nombre d'emprunts en retard lus: ")
                .append(item.getReadCount()).append(System.lineSeparator())
                .append("Nombre d'emprunts filtrés (même emprunteur) : ")
                .append(item.getFilterCount()).append(System.lineSeparator())
                .append("Nombre d'emprunts rejetés: ")
                .append(item.getProcessSkipCount()).append(System.lineSeparator())
                .append("Nombre d'utilisateurs relancés: ")
                .append(item.getWriteCount()).append(System.lineSeparator())
                .append("Erreurs: ")
                .append(item.getFailureExceptions()).append(System.lineSeparator())
                .toString());
        writer.setAppendAllowed(true);
        return writer;
    }

    public void writeHeader() {
        Path path = Paths.get(biblioJobProperties.getReportPath(),
                String.format("reminder-job-execution-%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        this.setResource(new FileSystemResource(path.toString()));
        this.setHeaderCallback(headerWriter -> headerWriter.append("--- Rapport d'execution du Batch de relance ---"));
        ExecutionContext executionContext = new ExecutionContext();
        this.open(executionContext);
    }
}
