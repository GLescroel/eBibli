package com.ebibli.batch.listener;

import com.ebibli.batch.writer.AbstractJobExecutionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import static java.util.Collections.singletonList;

public class BatchJobExecutionListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobExecutionListener.class);
    private final AbstractJobExecutionWriter itemWriter;

    public BatchJobExecutionListener(AbstractJobExecutionWriter itemWriter) {
        this.itemWriter = itemWriter;
    }

    private static String getStepExecutionSummary(StepExecution stepExecution) {
        return stepExecution.getSummary();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.debug("BatchJobExecutionListener - beforeJob {}", getStepExecutionSummary(stepExecution));
        itemWriter.writeHeader();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.debug("BatchJobExecutionListener - afterJob {}", getStepExecutionSummary(stepExecution));
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
