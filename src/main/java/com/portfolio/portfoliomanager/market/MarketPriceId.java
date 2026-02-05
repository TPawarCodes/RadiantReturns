package com.portfolio.portfoliomanager.market;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Data
public class MarketPriceId implements Serializable {

    private Long assetId;
    private LocalDate priceDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketPriceId that = (MarketPriceId) o;
        return Objects.equals(assetId, that.assetId) && Objects.equals(priceDate, that.priceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, priceDate);
    }
}