package com.example.Customer.Website.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobOperator jobOperator;
    @Autowired
    private Job job;

    @GetMapping(value = "/job")
    public String testJob(@RequestParam(name = "id") String jobId) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        if (StringUtils.hasLength(jobId)) {
            jobParametersBuilder.addString("jobId", jobId);
        }
        JobExecution jobExecution;
        try {
            jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
            // return exception message
            return e.getMessage();
        }
        // return job execution status
        return jobExecution.getStatus().name();
    }

    @GetMapping("/stop/all")
    public String stopRunningExecutions(Model model) {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions(job.getName());
            jobOperator.stop(executions.iterator().next());
        } catch (NoSuchJobException | NoSuchJobExecutionException | JobExecutionNotRunningException e) {
            e.printStackTrace();
        }
        return job.getName() + "has stopped";
    }
}