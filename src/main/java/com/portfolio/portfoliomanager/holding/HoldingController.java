package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.dto.CreateHoldingRequest;
import com.portfolio.portfoliomanager.dto.CreateHoldingWithSymbolRequest;
import com.portfolio.portfoliomanager.dto.HoldingDetailDto;
import com.portfolio.portfoliomanager.dto.HoldingDto;
import com.portfolio.portfoliomanager.dto.UpdateHoldingRequest;
import com.portfolio.portfoliomanager.holding.Holding;
import com.portfolio.portfoliomanager.service.HoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/holdings", "/api/holdings"})
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HoldingController {

    private final HoldingService holdingService;

    @PostMapping
    public HoldingDto createHolding(@RequestBody CreateHoldingRequest request) {
        Holding holding = new Holding();
        holding.setPortfolioId(request.getPortfolioId());
        holding.setAssetId(request.getAssetId());
        holding.setQuantity(request.getQuantity());
        holding.setAvgBuyPrice(request.getAvgBuyPrice());
        holding.setInvestedValue(request.getInvestedValue());
        Holding saved = holdingService.createHolding(holding);
        return mapToDto(saved);
    }

    @PostMapping("/create")
    public HoldingDetailDto createHoldingFromSymbol(@RequestBody CreateHoldingWithSymbolRequest request) {
        Holding saved = holdingService.createHoldingFromSymbol(request);
        return holdingService.getHoldingDetails(saved.getPortfolioId()).stream()
                .filter(h -> h.getId().equals(saved.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Holding not found after create"));
    }

    @GetMapping
    public List<HoldingDto> getHoldings(@RequestParam Long portfolioId) {
        return holdingService.getHoldingsByPortfolioId(portfolioId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/details")
    public List<HoldingDetailDto> getHoldingDetails(@RequestParam Long portfolioId) {
        return holdingService.getHoldingDetails(portfolioId);
    }

    @PutMapping("/{holdingId}")
    public HoldingDetailDto updateHolding(@PathVariable Long holdingId, @RequestBody UpdateHoldingRequest request) {
        Holding updated = holdingService.updateHolding(holdingId, request);
        return holdingService.getHoldingDetails(updated.getPortfolioId()).stream()
                .filter(h -> h.getId().equals(updated.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Holding not found after update"));
    }

    @DeleteMapping("/{holdingId}")
    public void deleteHolding(@PathVariable Long holdingId) {
        holdingService.deleteHolding(holdingId);
    }

    @PostMapping("/{holdingId}/categories/{categoryId}")
    public void addCategoryToHolding(@PathVariable Long holdingId, @PathVariable Long categoryId) {
        holdingService.addCategoryToHolding(holdingId, categoryId);
    }

    private HoldingDto mapToDto(Holding holding) {
        HoldingDto dto = new HoldingDto();
        dto.setId(holding.getId());
        dto.setPortfolioId(holding.getPortfolioId());
        dto.setAssetId(holding.getAssetId());
        dto.setQuantity(holding.getQuantity());
        dto.setAvgBuyPrice(holding.getAvgBuyPrice());
        dto.setInvestedValue(holding.getInvestedValue());
        return dto;
    }

}
