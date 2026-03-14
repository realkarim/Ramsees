package com.realkarim.ramesses.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.average-slots-splits")
public class SlotsConfigProps {
    private int fast;
    private int slow;
}
