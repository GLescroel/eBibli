package com.ebibli.batch.writer;

import com.ebibli.batch.config.BiblioJobProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class BatchStepExecutionWriterTest {

    private BiblioJobProperties biblioJobProperties = new BiblioJobProperties();

    private String reportFileName = "report_test.txt";
    private String reportHeader = "header test";
    private String readerText = "reader text test";
    private String processorFilterText = "processor filter text test";
    private String processorSkipText = "processor skip text test";
    private String writerText = "processor writer text test";

    @Before
    public void setup() {
        biblioJobProperties.setChunkSize(10);
        biblioJobProperties.setSkipLimit(10);
        biblioJobProperties.setReportPath("c:/temp/");
    }

    @Test
    public void testShouldWriteReport() throws Exception {
        BatchStepExecutionWriter batchStepExecutionWriter = new BatchStepExecutionWriter(biblioJobProperties, reportFileName,
                reportHeader, readerText, processorFilterText, processorSkipText, writerText);

        batchStepExecutionWriter.initialize();
        batchStepExecutionWriter.writeHeader();
        batchStepExecutionWriter.close();

        String path = "c:\\temp\\" + reportFileName;
        Resource applicationFile = new FileSystemResource(path);
        Assert.assertTrue(applicationFile.exists());
    }
}