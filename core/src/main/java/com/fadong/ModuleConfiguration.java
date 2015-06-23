package com.fadong;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 14. 12. 23.
 */
@Configuration
@EntityScan(basePackages = "com.fadong.domain")
@ComponentScan(basePackages = "com.fadong")
public class ModuleConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}