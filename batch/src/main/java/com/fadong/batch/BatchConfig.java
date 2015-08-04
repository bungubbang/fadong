package com.fadong.batch;

import com.fadong.CoreConfiguration;
import com.fadong.batch.job.AllCardUpdateConfig;
import com.fadong.batch.job.PageAccessTokenConfig;
import com.fadong.batch.job.PageUpdateConfig;
import com.fadong.batch.job.RecentCardUpdateConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 15.
 */
@Configuration
@EnableBatchProcessing
@Import({CoreConfiguration.class,
        PageUpdateConfig.class,
        RecentCardUpdateConfig.class,
        AllCardUpdateConfig.class,
        PageAccessTokenConfig.class})
public class BatchConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
