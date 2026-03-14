package com.realkarim.ramesses.ramesses.port.out;

import com.realkarim.ramesses.ramesses.domain.model.MarketBar;
import java.util.List;

public interface MarketDataPort {
    List<MarketBar> getLatestBars();
}
