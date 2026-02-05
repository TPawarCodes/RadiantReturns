package com.portfolio.portfoliomanager.dto;

import lombok.Data;

@Data
public class CreateAssetRequest {

    private String symbol;

    private String name;

    private String exchangeOrSource;

    private String currency;

    private Long assetTypeId;

    private String metadata;

}
