package com.portfolio.portfoliomanager.asset;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "assets")
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private String name;

    private String exchangeOrSource;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "asset_type_id")
    private AssetType assetType;

    @Column(columnDefinition = "JSON")
    private String metadata;

}
