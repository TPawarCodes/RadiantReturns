package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.dto.CreatePortfolioRequest;
import com.portfolio.portfoliomanager.dto.PortfolioDto;
import com.portfolio.portfoliomanager.portfolio.Portfolio;
import com.portfolio.portfoliomanager.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public PortfolioDto createPortfolio(@RequestBody CreatePortfolioRequest request) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.getName());
        portfolio.setBaseCurrency(request.getBaseCurrency());
        Portfolio saved = portfolioService.createPortfolio(portfolio);
        return mapToDto(saved);
    }

    @GetMapping
    public List<PortfolioDto> getAllPortfolios() {
        return portfolioService.getAllPortfolios().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PortfolioDto getPortfolio(@PathVariable Long id) {
        return mapToDto(portfolioService.getPortfolioById(id));
    }

    private PortfolioDto mapToDto(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        dto.setBaseCurrency(portfolio.getBaseCurrency());
        dto.setCreatedAt(portfolio.getCreatedAt());
        return dto;
    }

}