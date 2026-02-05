package com.portfolio.portfoliomanager.market;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "market_price")
@Data
public class MarketPrice {

    @EmbeddedId
    private MarketPriceId id;

    private BigDecimal price;
}