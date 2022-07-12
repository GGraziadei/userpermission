package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.batch.ReportKey;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.reports.Report;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private Job allReportJob;

    @Autowired
    private Job userReportJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private UserDAO userService;

    @PostMapping
    public Report allReport() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        UUID jobName = UUID.randomUUID();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        this.jobLauncher.run(this.allReportJob, jobParametersBuilder
                .addString(ReportKey.JOB_NAME, String.valueOf(jobName))
                .toJobParameters());
        Report report = Report.builder()
                .fileName(jobName.toString())
                .jobName(jobName)
                .build();
        return report;
    }

    @PostMapping("/{userId}")
    public Report userReport(@PathVariable String userId) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        UUID jobName = UUID.randomUUID();
        User user = this.userService.get(UUID.fromString(userId)).orElseThrow();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        this.jobLauncher.run(this.userReportJob, jobParametersBuilder
                .addString(ReportKey.JOB_NAME, String.valueOf(jobName))
                .addString(ReportKey.USER_ID, userId)
                .toJobParameters());
        Report report = Report.builder()
                .fileName(jobName.toString())
                .jobName(jobName)
                .user(user)
                .build();
        return report;
    }

}
