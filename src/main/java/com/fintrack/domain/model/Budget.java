package com.fintrack.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(hidden = true)
@Entity
@NoArgsConstructor
@Getter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal totalBudget;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Budget(User user, LocalDate date, BigDecimal totalBudget, LocalDateTime createdAt) {
        this.user = user;
        this.date = date;
        this.totalBudget = totalBudget;
        this.createdAt = createdAt;
    }

    public boolean isExceeded(BigDecimal totalExpense) {
        return totalExpense.compareTo(this.totalBudget) > 0;
    }

}
