package com.ebibli.batch.listener;

import com.ebibli.batch.writer.ReservationJobExecutionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import static java.util.Collections.singletonList;

public class ReservationJobExecutionListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationJobExecutionListener.class);
    private final ReservationJobExecutionWriter itemWriter;

    public ReservationJobExecutionListener(ReservationJobExecutionWriter itemWriter) {
        this.itemWriter = itemWriter;
    }

    private static String getStepExecutionSummary(StepExecution stepExecution) {
        return stepExecution.getSummary();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.debug("ReservationJobExecutionListener - beforeJob {}", getStepExecutionSummary(stepExecution));
        itemWriter.writeHeader();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.debug("ReservationJobExecutionListener - afterJob {}", getStepExecutionSummary(stepExecution));
        try {
            itemWriter.write(singletonList(stepExecution));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            itemWriter.close();
        }
        return stepExecution.getExitStatus();
    }
}
