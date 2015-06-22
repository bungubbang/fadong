package com.fadong;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.fadong.repository")
public class PersistenceConfiguration {

//    @Bean
//    @Profile("local")
//    public DataSource localDataSource(Environment env) {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.username"));
//        dataSource.setPassword(env.getProperty("jdbc.password"));
//        return dataSource;
//    }

//    @Bean
//    @Profile("production")
//    public DataSource dataSource(Environment env) {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.username"));
//        dataSource.setPassword(env.getProperty("jdbc.password"));
//        dataSource.setTestWhileIdle(false);
//        dataSource.setTestWhileIdle(false);
//        dataSource.setTestOnBorrow(false);
//        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
//        dataSource.setTestOnReturn(false);
//        dataSource.setValidationQueryTimeout(30);
//        dataSource.setDefaultQueryTimeout(3);
//        dataSource.setTimeBetweenEvictionRunsMillis(30000);
//        dataSource.setMinEvictableIdleTimeMillis(30000);
//        dataSource.setMaxTotal(100);
//        dataSource.setMinIdle(100);
//        dataSource.setInitialSize(100);
//        dataSource.setMaxWaitMillis(3000);
//        dataSource.setAbandonedUsageTracking(true);
//        dataSource.setRemoveAbandonedTimeout(60);
//        dataSource.setLogAbandoned(true);
//        return dataSource;
//    }


}
