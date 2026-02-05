package com.portfolio.portfoliomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSymbolDto {
    private String symbol;
    private String description;
    private String displaySymbol;
    private String currency;
    private String exchange;
}
