package com.realkarim.ramesses.domain.model;

import lombok.Value;

@Value
public class Portfolio {
    double budget;
    double ethHoldings;
    PortfolioStep step;
    double entryPrice;
    double realizedPnl;

    public static Portfolio initial(double budget) {
        return new Portfolio(budget, 0.0, PortfolioStep.BUY_NEXT, 0.0, 0.0);
    }
}
