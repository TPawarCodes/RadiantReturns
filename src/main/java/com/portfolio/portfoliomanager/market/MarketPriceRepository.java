package com.portfolio.portfoliomanager.market;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, MarketPriceId> {

    Optional<MarketPrice> findTopByIdAssetIdOrderByIdPriceDateDesc(Long assetId);
}