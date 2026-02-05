package com.portfolio.portfoliomanager.repository;

import com.portfolio.portfoliomanager.holding.HoldingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingCategoryRepository extends JpaRepository<HoldingCategory, Long> {

	List<HoldingCategory> findByHoldingId(Long holdingId);

	void deleteByHoldingId(Long holdingId);

}
