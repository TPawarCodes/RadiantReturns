package com.portfolio.portfoliomanager.asset;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "asset_types")
@Data
public class AssetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

}
