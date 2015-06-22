package com.fadong.batch.job;

import com.fadong.batch.ReportJobListener;
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
public class PageUpdateConfig {

    private static final Log logger = LogFactory.getLog(PageUpdateConfig.class);

    private static final String BATCH_JOB_NAME = "pageUpdateJob";
    private static final String BATCH_STEP_NAME = "pageUpdateStep";

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private Step accessTokenStep;

    @Autowired
    private BatchService batchService;

    @Bean
    public ItemReader<Page> pageUpdateReader() {
        JpaPagingItemReader<Page> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select p from Page p");
        return itemReader;
    }

    @Bean
    public ItemProcessor<Page, Page> pageUpdateProcessor() {
        return new PageUpdateProcessor();
    }

    @Bean
    public ItemWriter<Page> pageUpdateWriter() {
        JpaItemWriter<Page> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    @Bean
    public Step pageUpdateStep(StepBuilderFactory stepBuilder) {
        return stepBuilder.get(BATCH_STEP_NAME)
                .<Page, Page>chunk(100)
                .reader(pageUpdateReader())
                .processor(pageUpdateProcessor())
                .listener(new PageUpdateLogListener())
                .writer(pageUpdateWriter())
                .build();
    }

    @Bean
    public Job pageUpdateJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        return jobBuilder.get(BATCH_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(accessTokenStep)
                .next(pageUpdateStep(stepBuilder))
                .listener(new ReportJobListener())
                .build();
    }



    class PageUpdateProcessor implements ItemProcessor<Page, Page> {

        @Override
        public Page process(Page item) throws Exception {
            return batchService.updatePage(item);
        }
    }


    class PageUpdateLogListener implements ItemProcessListener<Page, Page> {

        @Override
        public void beforeProcess(Page item) {
        }

        @Override
        public void afterProcess(Page item, Page result) {
            logger.info("update page : " + result.toString());
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
