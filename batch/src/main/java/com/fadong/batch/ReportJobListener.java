package com.fadong.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 15.
 */
public class ReportJobListener implements JobExecutionListener {

    private static final Log LOGGER = LogFactory.getLog(ReportJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        protocol.append("  Start     : " + jobExecution.getStartTime()+"\n");
        protocol.append("  JobId      : "+ jobExecution.getJobId()+"\n");
        protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

        LOGGER.info(protocol.toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        StringBuilder protocol = new StringBuilder();
        protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        protocol.append("Protocol for " + jobExecution.getJobInstance().getJobName() + " \n");
        protocol.append("  Started     : "+ jobExecution.getStartTime()+"\n");
        protocol.append("  Finished    : "+ jobExecution.getEndTime()+"\n");
        protocol.append("  During      : "+ (jobExecution.getEndTime().getTime()-jobExecution.getStartTime().getTime())+"\n");
        protocol.append("  Exit-Code   : "+ jobExecution.getExitStatus().getExitCode()+"\n");
        protocol.append("  Exit-Descr. : "+ jobExecution.getExitStatus().getExitDescription()+"\n");
        protocol.append("  Status      : "+ jobExecution.getStatus()+"\n");
        protocol.append("  JobId      : "+ jobExecution.getJobId()+"\n");
        protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

        protocol.append("Job-Parameter: \n");
        JobParameters jp = jobExecution.getJobParameters();
        for (Iterator<Map.Entry<String,JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String,JobParameter> entry = iter.next();
            protocol.append("  "+entry.getKey()+"="+entry.getValue()+"\n");
        }
        protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
            protocol.append("Step " + stepExecution.getStepName() + " \n");
            protocol.append("WriteCount: " + stepExecution.getWriteCount() + "\n");
            protocol.append("ReadCount: " + stepExecution.getReadCount() + "\n");
            protocol.append("ReadSkipCount: " + stepExecution.getReadSkipCount() + "\n");
            protocol.append("Commits: " + stepExecution.getCommitCount() + "\n");
            protocol.append("SkipCount: " + stepExecution.getSkipCount() + "\n");
            protocol.append("Rollbacks: " + stepExecution.getRollbackCount() + "\n");
            protocol.append("Filter: " + stepExecution.getFilterCount() + "\n");
            protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        }

        LOGGER.info(protocol.toString());
    }
}
