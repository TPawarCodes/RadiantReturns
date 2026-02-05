package com.portfolio.portfoliomanager.holding;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "holding_categories")
@Data
public class HoldingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "holding_id")
    private Long holdingId;

    @Column(name = "category_id")
    private Long categoryId;

}
