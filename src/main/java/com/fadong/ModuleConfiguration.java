package com.fadong;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 14. 12. 23.
 */
@Configuration
@ComponentScan(basePackages = "com.fadong")
public class ModuleConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
