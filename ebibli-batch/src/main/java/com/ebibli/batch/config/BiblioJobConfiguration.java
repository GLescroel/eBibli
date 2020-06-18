package com.ebibli.batch.config;

import com.ebibli.batch.listener.BatchJobExecutionListener;
import com.ebibli.batch.processor.ReminderJobProcessor;
import com.ebibli.batch.processor.ReservationJobProcessor;
import com.ebibli.batch.reader.ReminderJobReader;
import com.ebibli.batch.reader.ReservationJobReader;
import com.ebibli.batch.writer.BatchStepExecutionWriter;
import com.ebibli.batch.writer.ReminderJobWriter;
import com.ebibli.batch.writer.ReservationJobWriter;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.emailconfiguration.EmailConfiguration;
import com.ebibli.service.EmpruntService;
import com.ebibli.service.ReservationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.internet.MimeMessage;

@EnableBatchProcessing
@Configuration
public class BiblioJobConfiguration extends DefaultBatchConfigurer {

    private static final String REMINDER_REPORT_FILE_NAME = "reminder-job-execution-%s.txt";
    private static final String REMINDER_REPORT_HEADER = "--- Rapport d'execution du Batch de relance ---";
    private static final String REMINDER_REDADER_TEXT = "Nombre d'emprunts en retard lus: ";
    private static final String REMINDER_PROCESSOR_FILTER_TEXT = "Nombre d'emprunts filtrés (même emprunteur) : ";
    private static final String REMINDER_PROCESSOR_SKIP_TEXT = "Nombre d'emprunts rejetés: ";
    private static final String REMINDER_WRITER_TEXT = "Nombre d'utilisateurs relancés: ";
    private static final String RESERVATION_REPORT_FILE_NAME = "reservation-job-execution-%s.txt";
    private static final String RESERVATION_REPORT_HEADER = "--- Rapport d'execution du Batch des réservations ---";
    private static final String RESERVATION_REDADER_TEXT = "Nombre de réservation dépassées lues : ";
    private static final String RESERVATION_PROCESSOR_FILTER_TEXT = "Nombre de réservations filtrés : ";
    private static final String RESERVATION_PROCESSOR_SKIP_TEXT = "Nombre de réservations rejetés: ";
    private static final String RESERVATION_WRITER_TEXT = "Nombre de réservations annulées et d'email envoyés : ";
    @Autowired
    private BiblioJobProperties biblioJobProperties;
    @Autowired
    private EmailConfiguration emailConfiguration;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Bean
    @JobScope
    public ListItemReader<EmpruntDto> reminderItemReader(EmpruntService empruntService) {
        return new ReminderJobReader(empruntService.getAllLivresEnRetard());
    }

    @Bean
    @JobScope
    public ListItemReader<ReservationDto> reservationItemReader(ReservationService reservationService) {
        return new ReservationJobReader(reservationService.getAllReservationToCancel());
    }

    @StepScope
    @Bean
    public ItemProcessor<EmpruntDto, MimeMessage> reminderItemProcessor(EmpruntService empruntService) {
        return new ReminderJobProcessor(empruntService, emailConfiguration);
    }

    @StepScope
    @Bean
    public ItemProcessor<ReservationDto, MimeMessage> reservationItemProcessor(ReservationService reservationService) {
        return new ReservationJobProcessor(reservationService, emailConfiguration);
    }

    @Bean
    @StepScope
    public ReminderJobWriter reminderItemWriter() {
        return new ReminderJobWriter(emailConfiguration.javaMailSender());
    }

    @Bean
    @StepScope
    public ReservationJobWriter reservationItemWriter() {
        return new ReservationJobWriter(emailConfiguration.javaMailSender());
    }

    @Bean
    public Job job(JobBuilderFactory jobs, Step firstStep, Step secondStep) {
        return jobs.get("bibliJob")
                .start(firstStep)
                .next(secondStep)
                .build();
    }

    /**
     * définition du workflow du job de controle des emprunts en retard
     *
     * @param stepBuilderFactory
     * @param itemWriter
     * @param reminderItemProcessor
     */
    @Bean
    public Step firstStep(
            StepBuilderFactory stepBuilderFactory,
            ReminderJobWriter itemWriter,
            ListItemReader<EmpruntDto> reminderItemReader,
            ItemProcessor<EmpruntDto, MimeMessage> reminderItemProcessor) {
        return stepBuilderFactory.get("step1")
                .<EmpruntDto, MimeMessage>chunk(biblioJobProperties.getChunkSize())
                .reader(reminderItemReader)
                .processor(reminderItemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true)
                .listener(reminderJobExecutionListener())
                .build();
    }

    /**
     * définition du workflow du job de controle des réservations
     *
     * @param stepBuilderFactory
     * @param reservationItemWriter
     * @param reservationItemProcessor
     */
    @Bean
    public Step secondStep(
            StepBuilderFactory stepBuilderFactory,
            ReservationJobWriter reservationItemWriter,
            ListItemReader<ReservationDto> reservationItemReader,
            ItemProcessor<ReservationDto, MimeMessage> reservationItemProcessor) {
        return stepBuilderFactory.get("step2")
                .<ReservationDto, MimeMessage>chunk(biblioJobProperties.getChunkSize())
                .reader(reservationItemReader)
                .processor(reservationItemProcessor)
                .writer(reservationItemWriter)
                .allowStartIfComplete(true)
                .listener(reservationJobExecutionListener())
                .build();
    }

    @Bean
    public StepExecutionListener reminderJobExecutionListener() {
        return new BatchJobExecutionListener(reminderJobExecutionFileWriter());
    }

    @Bean
    public StepExecutionListener reservationJobExecutionListener() {
        return new BatchJobExecutionListener(reservationJobExecutionFileWriter());
    }

    @Bean
    public BatchStepExecutionWriter reminderJobExecutionFileWriter() {
        return new BatchStepExecutionWriter(biblioJobProperties, REMINDER_REPORT_FILE_NAME, REMINDER_REPORT_HEADER,
                REMINDER_REDADER_TEXT, REMINDER_PROCESSOR_FILTER_TEXT, REMINDER_PROCESSOR_SKIP_TEXT, REMINDER_WRITER_TEXT).initialize();
    }

    @Bean
    public BatchStepExecutionWriter reservationJobExecutionFileWriter() {
        return new BatchStepExecutionWriter(biblioJobProperties, RESERVATION_REPORT_FILE_NAME, RESERVATION_REPORT_HEADER,
                RESERVATION_REDADER_TEXT, RESERVATION_PROCESSOR_FILTER_TEXT, RESERVATION_PROCESSOR_SKIP_TEXT, RESERVATION_WRITER_TEXT).initialize();
    }

    @Scheduled(cron = "${batch.cron.value}")
    public void perform() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters params = new JobParametersBuilder()
                .addString("bibliJob", String.valueOf(System.currentTimeMillis()))
//                .addString("reservationJob", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }
}

