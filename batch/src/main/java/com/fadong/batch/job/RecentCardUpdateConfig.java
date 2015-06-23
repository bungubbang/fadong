package com.fadong.batch.job;

import com.fadong.batch.ReportJobListener;
import com.fadong.domain.Card;
import com.fadong.domain.Page;
import com.fadong.service.BatchService;
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

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Date;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 15.
 */
@Configuration
public class RecentCardUpdateConfig {

    private static final Log logger = LogFactory.getLog(RecentCardUpdateConfig.class);

    private static final String BATCH_JOB_NAME = "cardRecentUpdateJob";
    private static final String BATCH_STEP_NAME = "cardRecentUpdateStep";

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private BatchService batchService;

    @Autowired
    private Step accessTokenStep;

    @Bean
    public ItemReader<Page> cardRecentUpdateItemReader() {
        JpaPagingItemReader<Page> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select p from Page p");
        return itemReader;
    }

    @Bean
    public ItemProcessor<Page, Page> cardRecentUpdateProcessor() {
        return new RecentCardUpdateProcessor();
    }

    @Bean
    public ItemWriter<Page> cardRecentUpdateWriter() {
        JpaItemWriter<Page> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    @Bean
    public Step cardUpdateStep(StepBuilderFactory stepBuilder) {
        return stepBuilder.get(BATCH_STEP_NAME)
                .<Page, Page>chunk(100)
                .reader(cardRecentUpdateItemReader())
                .processor(cardRecentUpdateProcessor())
                .listener(new RecentCardUpdateLogListener())
                .writer(cardRecentUpdateWriter())
                .build();
    }

    @Bean
    public Job cardUpdateJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        return jobBuilder.get(BATCH_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(accessTokenStep)
                .next(cardUpdateStep(stepBuilder))
                .listener(new ReportJobListener())
                .build();
    }



    class RecentCardUpdateProcessor implements ItemProcessor<Page, Page> {

        @Override
        public Page process(Page item) throws Exception {
            return batchService.updateCardRecently(item);
        }
    }


    class RecentCardUpdateLogListener implements ItemProcessListener<Page, Page> {

        @Override
        public void beforeProcess(Page item) {
        }

        @Override
        public void afterProcess(Page item, Page result) {
            logger.info("Card update page : " + item.getId() + ",\tname : " + item.getName());
        }

        @Override
        public void onProcessError(Page item, Exception e) {
            if(item != null) {
                logger.error("Error to processor : " + item.getName());
            }
            e.printStackTrace();
        }
    }
}
