package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.asset.Asset;
import com.portfolio.portfoliomanager.dto.AssetDto;
import com.portfolio.portfoliomanager.dto.CreateAssetRequest;
import com.portfolio.portfoliomanager.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public AssetDto createAsset(@RequestBody CreateAssetRequest request) {
        Asset savedAsset = assetService.createAsset(request);
        return mapToDto(savedAsset);
    }

    @GetMapping
    public List<AssetDto> getAllAssets() {
        return assetService.getAllAssets().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AssetDto mapToDto(Asset asset) {
        AssetDto dto = new AssetDto();
        dto.setId(asset.getId());
        dto.setSymbol(asset.getSymbol());
        dto.setName(asset.getName());
        dto.setExchangeOrSource(asset.getExchangeOrSource());
        dto.setCurrency(asset.getCurrency());
        dto.setAssetTypeId(asset.getAssetType().getId());
        dto.setMetadata(asset.getMetadata());
        return dto;
    }

}
