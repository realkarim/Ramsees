package com.tyche.ramsees.config;

import com.tyche.ramsees.domain.strategy.MacDStrategy;
import com.tyche.ramsees.domain.strategy.TradingStrategy;
import com.tyche.ramsees.domain.usecase.CheckMarketUseCase;
import com.tyche.ramsees.port.in.MarketCheckPort;
import com.tyche.ramsees.port.out.MarketDataPort;
import com.tyche.ramsees.port.out.TradeExecutionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final StrategyConfigProps strategyConfig;

    @Bean
    public TradingStrategy tradingStrategy(
        @Value("${application.trading.max-bars:500}") int maxBars) {
        return new MacDStrategy(strategyConfig, maxBars);
    }

    @Bean
    public MarketCheckPort marketCheckPort(MarketDataPort marketData,
        TradeExecutionPort tradeExecution, TradingStrategy strategy) {
        return new CheckMarketUseCase(marketData, tradeExecution, strategy);
    }
}
