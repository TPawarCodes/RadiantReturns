package com.portfolio.portfoliomanager.repository;

import com.portfolio.portfoliomanager.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}