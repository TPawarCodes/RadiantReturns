package com.portfolio.portfoliomanager.repository;

import com.portfolio.portfoliomanager.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

}
