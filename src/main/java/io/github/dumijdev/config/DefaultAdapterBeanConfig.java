package io.github.dumijdev.config;

import io.github.dumijdev.processor.Adapter;
import io.github.dumijdev.processor.DefaultAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAdapterBeanConfig {

    @Bean
    @ConditionalOnMissingBean
    public Adapter adapter() {
        return new DefaultAdapter();
    }
}
