package com.portfolio.portfoliomanager.holding;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "holdings", indexes = {
    @Index(name = "idx_holding_portfolio", columnList = "portfolio_id"),
    @Index(name = "idx_holding_asset", columnList = "asset_id")
})
@Data
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "asset_id")
    private Long assetId;

    private BigDecimal quantity;

    @Column(name = "avg_buy_price")
    private BigDecimal avgBuyPrice;

    @Column(name = "invested_value")
    private BigDecimal investedValue;

    @Column(name = "buy_date")
    private LocalDate buyDate;

}
