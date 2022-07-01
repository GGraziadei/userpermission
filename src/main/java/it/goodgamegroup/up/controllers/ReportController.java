package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.batch.ReportKey;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
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
 private  JobLauncher jobLauncher;

  @PostMapping
  public String allReport() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    UUID jobName = UUID.randomUUID();
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    this.jobLauncher.run(this.allReportJob , jobParametersBuilder
                    .addString(ReportKey.JOB_NAME , String.valueOf(jobName))
                    .toJobParameters());
    return jobName.toString();
  }

  @PostMapping("/{userId}")
  public String allReport(@PathVariable String userId) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    UUID jobName = UUID.randomUUID();
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    this.jobLauncher.run(this.allReportJob , jobParametersBuilder
            .addString(ReportKey.JOB_NAME , String.valueOf(jobName))
            .addString(ReportKey.USER_ID , userId)
            .toJobParameters());
    return jobName.toString();
  }

}
