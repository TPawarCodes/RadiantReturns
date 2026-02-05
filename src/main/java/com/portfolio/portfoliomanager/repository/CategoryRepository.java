package com.portfolio.portfoliomanager.repository;

import com.portfolio.portfoliomanager.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}