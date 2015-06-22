package com.fadong.batch.job;

import com.fadong.batch.ReportJobListener;
import com.fadong.domain.AccessToken;
import com.fadong.service.AccessTokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 16.
 */
@Configuration
public class AccessTokenConfig {

    private static final Log logger = LogFactory.getLog(AccessTokenConfig.class);

    private static final String BATCH_JOB_NAME = "accessTokenJob";
    private static final String BATCH_STEP_NAME = "accessTokenStep";

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private AccessTokenService accessTokenService;

    @Bean
    public Job accessTokenJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        return jobBuilder.get(BATCH_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(accessTokenStep(stepBuilder))
                .listener(new ReportJobListener())
                .build();
    }

    @Bean
    public Step accessTokenStep(StepBuilderFactory stepBuilder) {
        return stepBuilder.get(BATCH_STEP_NAME)
                .<AccessToken, AccessToken>chunk(1)
                .reader(accessTokenReader())
                .processor(accessTokenProcessor())
                .listener(new AccessTokenLogListener())
                .writer(accessTokenWriter())
                .build();
    }

    @Bean
    public ItemReader<AccessToken> accessTokenReader() {
        JpaPagingItemReader<AccessToken> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select p from AccessToken p");
        return itemReader;
    }

    @Bean
    public ItemProcessor<AccessToken, AccessToken> accessTokenProcessor() {
        return new AccessTokenProcessor();
    }

    @Bean
    public ItemWriter<AccessToken> accessTokenWriter() {
        JpaItemWriter<AccessToken> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    class AccessTokenProcessor implements ItemProcessor<AccessToken, AccessToken> {

        @Override
        public AccessToken process(AccessToken item) throws Exception {
            return accessTokenService.refreshAccessToken(item);
        }
    }


    class AccessTokenLogListener implements ItemProcessListener<AccessToken, AccessToken> {

        @Override
        public void beforeProcess(AccessToken item) {
        }

        @Override
        public void afterProcess(AccessToken item, AccessToken result) {
            logger.info("accessToken : " + result);
        }

        @Override
        public void onProcessError(AccessToken item, Exception e) {
            if(item != null) {
                logger.error("Error to processor : " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
