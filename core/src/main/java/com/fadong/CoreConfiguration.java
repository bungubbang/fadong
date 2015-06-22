package com.fadong;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 15.
 */
@Configuration
@Import(value = {PersistenceConfiguration.class, ModuleConfiguration.class})
public class CoreConfiguration {
}
