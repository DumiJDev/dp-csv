package io.github.dumijdev.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.csv")
public class SpringDataCSVConfig {
    private String path;
    private boolean hasHeader;
}
