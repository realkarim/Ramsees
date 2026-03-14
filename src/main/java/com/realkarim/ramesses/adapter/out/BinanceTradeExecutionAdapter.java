package com.realkarim.ramesses.adapter.out;

import com.realkarim.ramesses.domain.model.Portfolio;
import com.realkarim.ramesses.domain.model.PortfolioStep;
import com.realkarim.ramesses.port.out.TradeExecutionPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinanceTradeExecutionAdapter implements TradeExecutionPort {

    private Portfolio portfolio;
    private final double fee;

    public BinanceTradeExecutionAdapter(
        @Value("${application.trading.initial-budget:1000.0}") double initialBudget,
        @Value("${application.trading.fee:0.001}") double fee) {
        this.fee = fee;
        this.portfolio = Portfolio.initial(initialBudget);
    }

    @Override
    public void buy(double price) {
        double eth = portfolio.getBudget() / price;
        eth -= eth * fee;
        portfolio = new Portfolio(0.0, eth, PortfolioStep.SELL_NEXT, price, portfolio.getRealizedPnl());
        log.info("Buying at price: {}", price);
        log.info("Budget: {}, ETH: {}", portfolio.getBudget(), portfolio.getEthHoldings());
    }

    @Override
    public void sell(double price) {
        double proceeds = portfolio.getEthHoldings() * price;
        proceeds -= proceeds * fee;
        double costBasis = portfolio.getEthHoldings() * portfolio.getEntryPrice() * (1 + fee);
        double tradePnl = proceeds - costBasis;
        double realizedPnl = portfolio.getRealizedPnl() + tradePnl;
        portfolio = new Portfolio(proceeds, 0.0, PortfolioStep.BUY_NEXT, 0.0, realizedPnl);
        log.info("Selling at price: {}", price);
        log.info("Trade P&L: {} USDT | Realized P&L: {} USDT", String.format("%.4f", tradePnl), String.format("%.4f", realizedPnl));
        log.info("Budget: {}, ETH: {}", portfolio.getBudget(), portfolio.getEthHoldings());
    }

    @Override
    public Portfolio getPortfolio() {
        return portfolio;
    }
}
