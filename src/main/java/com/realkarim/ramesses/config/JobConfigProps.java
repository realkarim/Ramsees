package com.realkarim.ramesses.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.scheduler")
public class JobConfigProps {
    private int frequency;
}
