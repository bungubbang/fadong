package com.fadong.batch.job;

import com.fadong.batch.ReportJobListener;
import com.fadong.domain.Card;
import com.fadong.service.BatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.jsr.step.batchlet.BatchletAdapter;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 22.
 */
@Configuration
public class AllCardUpdateConfig {

    private static final Log logger = LogFactory.getLog(AllCardUpdateConfig.class);

    private static final String BATCH_JOB_NAME = "cardAllUpdateJob";
    private static final String BATCH_STEP_NAME = "cardAllUpdateStep";

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private BatchService batchService;

    @Bean
    public ItemReader<Card> cardAllUpdateItemReader() {
        JpaPagingItemReader<Card> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select c from Card c");
        return itemReader;
    }

    @Bean
    public ItemProcessor<Card, Card> cardAllUpdateProcessor() {
        return new AllCardUpdateProcessor();
    }

    @Bean
    public ItemWriter<Card> cardAllUpdateWriter() {
        JpaItemWriter<Card> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    @Bean
    public Step cardAllUpdateStep(StepBuilderFactory stepBuilder) {
        return stepBuilder.get(BATCH_STEP_NAME)
                .<Card, Card>chunk(100)
                .reader(cardAllUpdateItemReader())
                .processor(cardAllUpdateProcessor())
                .listener(new AllCardUpdateLogListener())
                .writer(cardAllUpdateWriter())
                .faultTolerant()
                .skipLimit(20)
                .skip(HttpClientErrorException.class)
                .listener(new UpdateFailListener())
                .build();
    }

    @Bean
    public Job cardAllUpdateJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        return jobBuilder.get(BATCH_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(cardAllUpdateStep(stepBuilder))
                .listener(new ReportJobListener())
                .build();
    }



    class AllCardUpdateProcessor implements ItemProcessor<Card, Card> {

        @Override
        public Card process(Card item) throws Exception {
            return batchService.updateCardAll(item);
        }
    }


    class AllCardUpdateLogListener implements ItemProcessListener<Card, Card> {

        @Override
        public void beforeProcess(Card item) {
        }

        @Override
        public void afterProcess(Card item, Card result) {
            logger.info("Card All update cardId : " + item.getId() + ",\tname : " + item.getName());
        }

        @Override
        public void onProcessError(Card item, Exception e) {
            if(item != null) {
                logger.error("Error to processor : " + item.getId());
            }
            e.printStackTrace();
        }
    }

    class UpdateFailListener implements SkipListener<Card, Card> {


        @Override
        public void onSkipInRead(Throwable t) {

        }

        @Override
        public void onSkipInWrite(Card item, Throwable t) {

        }

        @Override
        public void onSkipInProcess(Card item, Throwable t) {
            logger.error("delete card : " + item.getId());
            batchService.removeCard(item);
        }
    }
}
