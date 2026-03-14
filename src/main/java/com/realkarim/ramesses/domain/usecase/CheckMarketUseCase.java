package com.realkarim.ramesses.domain.usecase;

import com.realkarim.ramesses.domain.model.PortfolioStep;
import com.realkarim.ramesses.domain.model.TradeSignal;
import com.realkarim.ramesses.domain.strategy.TradingStrategy;
import com.realkarim.ramesses.port.in.MarketCheckPort;
import com.realkarim.ramesses.port.out.MarketDataPort;
import com.realkarim.ramesses.port.out.TradeExecutionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CheckMarketUseCase implements MarketCheckPort {

    private final MarketDataPort marketData;
    private final TradeExecutionPort tradeExecution;
    private final TradingStrategy strategy;

    private int iteration = 0;

    @Override
    public void checkMarket() {
        log.info("-----------------------------------------------");
        log.info("Iteration {}", ++iteration);

        var bars = marketData.getLatestBars();
        var signal = strategy.evaluate(bars);
        var portfolio = tradeExecution.getPortfolio();
        double latestPrice = bars.get(bars.size() - 1).getClose();

        if (signal == TradeSignal.BUY && portfolio.getStep() == PortfolioStep.BUY_NEXT) {
            log.info("Entering the market");
            tradeExecution.buy(latestPrice);
        } else if (signal == TradeSignal.SELL && portfolio.getStep() == PortfolioStep.SELL_NEXT) {
            log.info("Exiting the market");
            tradeExecution.sell(latestPrice);
        }

        var updated = tradeExecution.getPortfolio();
        double equity = updated.getBudget() + updated.getEthHoldings() * latestPrice;
        log.info("Current budget: {}", updated.getBudget());
        log.info("Current ETH: {}", updated.getEthHoldings());
        log.info("Total equity: {} USDT", String.format("%.4f", equity));
        log.info("Realized P&L: {} USDT", String.format("%.4f", updated.getRealizedPnl()));
        if (updated.getStep() == PortfolioStep.SELL_NEXT) {
            double unrealizedPnl = updated.getEthHoldings() * (latestPrice - updated.getEntryPrice());
            log.info("Unrealized P&L: {} USDT", String.format("%.4f", unrealizedPnl));
        }
    }
}
