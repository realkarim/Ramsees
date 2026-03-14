package com.realkarim.ramesses.domain.model;

public class Portfolio {
    private final double budget;
    private final double ethHoldings;
    private final PortfolioStep step;
    private final double entryPrice;
    private final double realizedPnl;

    public Portfolio(double budget, double ethHoldings, PortfolioStep step, double entryPrice, double realizedPnl) {
        this.budget = budget;
        this.ethHoldings = ethHoldings;
        this.step = step;
        this.entryPrice = entryPrice;
        this.realizedPnl = realizedPnl;
    }

    public static Portfolio initial(double budget) {
        return new Portfolio(budget, 0.0, PortfolioStep.BUY_NEXT, 0.0, 0.0);
    }

    public double getBudget() { return budget; }
    public double getEthHoldings() { return ethHoldings; }
    public PortfolioStep getStep() { return step; }
    public double getEntryPrice() { return entryPrice; }
    public double getRealizedPnl() { return realizedPnl; }
}
