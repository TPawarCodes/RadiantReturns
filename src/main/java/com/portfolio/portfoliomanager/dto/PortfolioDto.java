package com.portfolio.portfoliomanager.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PortfolioDto {

    private Long id;

    private String name;

    private String baseCurrency;

    private LocalDateTime createdAt;

}