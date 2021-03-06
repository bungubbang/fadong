package com.fadong.batch;

import com.fadong.CoreConfiguration;
import com.fadong.ModuleConfiguration;
import com.fadong.batch.job.AllCardUpdateConfig;
import com.fadong.batch.job.PageUpdateConfig;
import com.fadong.batch.job.RecentCardUpdateConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 15.
 */
@SpringBootApplication
public class BatchApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BatchApplication.class, args);
    }
}
