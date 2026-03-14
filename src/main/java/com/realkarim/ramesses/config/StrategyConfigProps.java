package com.realkarim.ramesses.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.strategy")
public class StrategyConfigProps {
    private String name;
    private int trendEmaLength;
    private int macdShort;
    private int macdLong;
    private int macdSignalLength;
    private double stopGain;
    private double stopLoss;
}
