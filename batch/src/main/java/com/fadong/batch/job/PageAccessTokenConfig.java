package com.fadong.batch.job;

import com.fadong.batch.ReportJobListener;
import com.fadong.domain.PageToken;
import com.fadong.repository.AccessTokenRepository;
import com.fadong.repository.PageTokenRepository;
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
public class PageAccessTokenConfig {

    private static final Log logger = LogFactory.getLog(PageAccessTokenConfig.class);

    private static final String BATCH_JOB_NAME = "pageAccessTokenJob";
    private static final String BATCH_STEP_NAME = "pageAccessTokenStep";
    private static final String PAGE_ID = "401109003427796";

    private static final String TOKEN_URL = "https://graph.facebook.com/" + PAGE_ID + "?fields=access_token";


    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private PageTokenRepository pageTokenRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public Job PageTokenJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        return jobBuilder.get(BATCH_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(PageTokenStep(stepBuilder))
                .listener(new ReportJobListener())
                .build();
    }

    @Bean
    public Step PageTokenStep(StepBuilderFactory stepBuilder) {
        return stepBuilder.get(BATCH_STEP_NAME)
                .<PageToken, PageToken>chunk(1)
                .reader(PageTokenReader())
                .processor(PageTokenProcessor())
                .listener(new PageTokenLogListener())
                .writer(PageTokenWriter())
                .build();
    }

    @Bean
    public ItemReader<PageToken> PageTokenReader() {
        JpaPagingItemReader<PageToken> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select t from PageToken t");
        return itemReader;
    }

    @Bean
    public ItemProcessor<PageToken, PageToken> PageTokenProcessor() {
        return new PageTokenProcessor();
    }

    @Bean
    public ItemWriter<PageToken> PageTokenWriter() {
        JpaItemWriter<PageToken> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    class PageTokenProcessor implements ItemProcessor<PageToken, PageToken> {

        @Override
        public PageToken process(PageToken item) throws Exception {
            String accessToken = accessTokenRepository.findAll().get(0).getAccessToken();
            String url = TOKEN_URL + "&access_token=" + accessToken;
            PageToken pageToken = restTemplate.getForObject(url, PageToken.class);
            return pageToken;
        }
    }


    class PageTokenLogListener implements ItemProcessListener<PageToken, PageToken> {

        @Override
        public void beforeProcess(PageToken item) {
            logger.info("before PageToken : " + item);
        }

        @Override
        public void afterProcess(PageToken item, PageToken result) {
            logger.info("after PageToken : " + result);
        }

        @Override
        public void onProcessError(PageToken item, Exception e) {
            if(item != null) {
                logger.error("Error to processor : " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
