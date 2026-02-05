package com.portfolio.portfoliomanager.service;

import com.portfolio.portfoliomanager.asset.Asset;
import com.portfolio.portfoliomanager.asset.AssetType;
import com.portfolio.portfoliomanager.dto.CreateAssetRequest;
import com.portfolio.portfoliomanager.repository.AssetRepository;
import com.portfolio.portfoliomanager.repository.AssetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;

    public Asset createAsset(CreateAssetRequest request) {
        if (assetTypeRepository.count() == 0) {
            AssetType stock = new AssetType();
            stock.setCode("STOCK");
            assetTypeRepository.save(stock);
        }
        Long assetTypeId = request.getAssetTypeId() != null ? request.getAssetTypeId() : 1L;
        AssetType assetType = assetTypeRepository.findById(assetTypeId)
                .orElseThrow(() -> new RuntimeException("AssetType not found"));
        Asset asset = new Asset();
        asset.setSymbol(request.getSymbol());
        asset.setName(request.getName());
        asset.setExchangeOrSource(request.getExchangeOrSource());
        asset.setCurrency(request.getCurrency());
        asset.setAssetType(assetType);
        asset.setMetadata(request.getMetadata());
        return assetRepository.save(asset);
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

}
