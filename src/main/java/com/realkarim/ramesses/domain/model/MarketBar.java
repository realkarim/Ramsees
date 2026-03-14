package com.realkarim.ramesses.domain.model;

import lombok.Value;
import java.time.ZonedDateTime;

@Value
public class MarketBar {
    ZonedDateTime timestamp;
    double open;
    double high;
    double low;
    double close;
    double volume;
}
