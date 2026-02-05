package com.portfolio.portfoliomanager.service;

import com.portfolio.portfoliomanager.asset.Asset;
import com.portfolio.portfoliomanager.asset.AssetType;
import com.portfolio.portfoliomanager.category.Category;
import com.portfolio.portfoliomanager.dto.CategoryDto;
import com.portfolio.portfoliomanager.dto.CreateHoldingWithSymbolRequest;
import com.portfolio.portfoliomanager.dto.HoldingDetailDto;
import com.portfolio.portfoliomanager.dto.UpdateHoldingRequest;
import com.portfolio.portfoliomanager.holding.Holding;
import com.portfolio.portfoliomanager.holding.HoldingCategory;
import com.portfolio.portfoliomanager.market.MarketPrice;
import com.portfolio.portfoliomanager.market.MarketPriceId;
import com.portfolio.portfoliomanager.market.MarketPriceRepository;
import com.portfolio.portfoliomanager.repository.AssetRepository;
import com.portfolio.portfoliomanager.repository.AssetTypeRepository;
import com.portfolio.portfoliomanager.repository.CategoryRepository;
import com.portfolio.portfoliomanager.repository.HoldingCategoryRepository;
import com.portfolio.portfoliomanager.repository.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoldingService {

    private final HoldingRepository holdingRepository;

    private final HoldingCategoryRepository holdingCategoryRepository;

    private final AssetRepository assetRepository;

    private final AssetTypeRepository assetTypeRepository;

    private final CategoryRepository categoryRepository;

    private final MarketPriceRepository marketPriceRepository;

    private final StockUniverseService stockUniverseService;

    public Holding createHolding(Holding holding) {
        return holdingRepository.save(holding);
    }

    public List<Holding> getHoldingsByPortfolioId(Long portfolioId) {
        return holdingRepository.findByPortfolioId(portfolioId);
    }

    public void addCategoryToHolding(Long holdingId, Long categoryId) {
        HoldingCategory hc = new HoldingCategory();
        hc.setHoldingId(holdingId);
        hc.setCategoryId(categoryId);
        holdingCategoryRepository.save(hc);
    }

    public Holding createHoldingFromSymbol(CreateHoldingWithSymbolRequest request) {
        Asset asset = assetRepository.findBySymbolIgnoreCase(request.getSymbol())
                .orElseGet(() -> createAsset(request));

        Holding holding = new Holding();
        holding.setPortfolioId(request.getPortfolioId());
        holding.setAssetId(asset.getId());
        holding.setQuantity(request.getQuantity());
        holding.setAvgBuyPrice(request.getAvgBuyPrice());
        holding.setInvestedValue(request.getQuantity().multiply(request.getAvgBuyPrice()));
        holding.setBuyDate(request.getBuyDate());
        Holding saved = holdingRepository.save(holding);

        stockUniverseService.getQuotePrice(request.getSymbol()).ifPresent(price -> {
            MarketPriceId priceId = new MarketPriceId();
            priceId.setAssetId(asset.getId());
            priceId.setPriceDate(LocalDate.now());
            MarketPrice marketPrice = marketPriceRepository.findById(priceId).orElse(new MarketPrice());
            marketPrice.setId(priceId);
            marketPrice.setPrice(price);
            marketPriceRepository.save(marketPrice);
        });

        setCategories(saved.getId(), request.getCategoryIds());
        return saved;
    }

    public Holding updateHolding(Long holdingId, UpdateHoldingRequest request) {
        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new RuntimeException("Holding not found: " + holdingId));

        if (request.getQuantity() != null) {
            holding.setQuantity(request.getQuantity());
        }
        if (request.getAvgBuyPrice() != null) {
            holding.setAvgBuyPrice(request.getAvgBuyPrice());
        }
        if (request.getBuyDate() != null) {
            holding.setBuyDate(request.getBuyDate());
        }

        if (holding.getQuantity() != null && holding.getAvgBuyPrice() != null) {
            holding.setInvestedValue(holding.getQuantity().multiply(holding.getAvgBuyPrice()));
        }

        Holding saved = holdingRepository.save(holding);
        if (request.getCategoryIds() != null) {
            setCategories(holdingId, request.getCategoryIds());
        }
        return saved;
    }

    public void deleteHolding(Long holdingId) {
        holdingCategoryRepository.deleteByHoldingId(holdingId);
        holdingRepository.deleteById(holdingId);
    }

    public List<HoldingDetailDto> getHoldingDetails(Long portfolioId) {
        return holdingRepository.findByPortfolioId(portfolioId).stream()
                .map(this::toDetailDto)
                .collect(Collectors.toList());
    }

    private void setCategories(Long holdingId, List<Long> categoryIds) {
        holdingCategoryRepository.deleteByHoldingId(holdingId);
        if (categoryIds == null) {
            return;
        }
        for (Long categoryId : categoryIds) {
            HoldingCategory hc = new HoldingCategory();
            hc.setHoldingId(holdingId);
            hc.setCategoryId(categoryId);
            holdingCategoryRepository.save(hc);
        }
    }

    private HoldingDetailDto toDetailDto(Holding holding) {
        Asset asset = assetRepository.findById(holding.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found: " + holding.getAssetId()));

        MarketPriceId priceId = new MarketPriceId();
        priceId.setAssetId(holding.getAssetId());
        priceId.setPriceDate(LocalDate.now());

        BigDecimal currentPrice = marketPriceRepository.findById(priceId)
                .map(MarketPrice::getPrice)
                .orElse(holding.getAvgBuyPrice());

        BigDecimal currentValue = holding.getQuantity().multiply(currentPrice);
        BigDecimal profitLoss = currentValue.subtract(holding.getInvestedValue());
        BigDecimal profitLossPercent = holding.getInvestedValue().compareTo(BigDecimal.ZERO) > 0
                ? profitLoss.multiply(BigDecimal.valueOf(100))
                .divide(holding.getInvestedValue(), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        List<CategoryDto> categories = holdingCategoryRepository.findByHoldingId(holding.getId()).stream()
                .map(hc -> categoryRepository.findById(hc.getCategoryId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::toCategoryDto)
                .collect(Collectors.toList());

        HoldingDetailDto dto = new HoldingDetailDto();
        dto.setId(holding.getId());
        dto.setPortfolioId(holding.getPortfolioId());
        dto.setAssetId(holding.getAssetId());
        dto.setSymbol(asset.getSymbol());
        dto.setName(asset.getName());
        dto.setQuantity(holding.getQuantity());
        dto.setAvgBuyPrice(holding.getAvgBuyPrice());
        dto.setCurrentPrice(currentPrice);
        dto.setInvestedValue(holding.getInvestedValue());
        dto.setCurrentValue(currentValue.setScale(2, RoundingMode.HALF_UP));
        dto.setProfitLoss(profitLoss.setScale(2, RoundingMode.HALF_UP));
        dto.setProfitLossPercent(profitLossPercent.setScale(2, RoundingMode.HALF_UP));
        dto.setBuyDate(holding.getBuyDate());
        dto.setCategories(categories);
        return dto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        return dto;
    }

    private Asset createAsset(CreateHoldingWithSymbolRequest request) {
        AssetType equityType = assetTypeRepository.findAll().stream()
                .filter(at -> "EQUITY".equalsIgnoreCase(at.getCode()))
                .findFirst()
                .orElseGet(() -> {
                    AssetType at = new AssetType();
                    at.setCode("EQUITY");
                    return assetTypeRepository.save(at);
                });

        Asset asset = new Asset();
        asset.setSymbol(request.getSymbol());
        asset.setName(request.getName());
        asset.setExchangeOrSource(request.getExchange());
        asset.setCurrency(request.getCurrency());
        asset.setAssetType(equityType);
        return assetRepository.save(asset);
    }

}
