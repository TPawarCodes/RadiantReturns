package com.portfolio.portfoliomanager.portfolio;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolios")
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String baseCurrency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}