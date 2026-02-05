package com.portfolio.portfoliomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioSummaryDto {
    private BigDecimal totalValue;
    private BigDecimal totalInvested;
    private BigDecimal totalProfitLoss;
    private BigDecimal totalReturnPercent;
    private BigDecimal dayChangeValue;
    private BigDecimal dayChangePercent;
    private List<HoldingResponseDto> holdings;
}