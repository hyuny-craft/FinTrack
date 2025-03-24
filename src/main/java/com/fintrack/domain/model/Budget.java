package com.fintrack.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

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

    @Column(nullable = false, name = "`month`")
    private YearMonth month;

    @Column(nullable = false)
    private BigDecimal totalBudget;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Budget(User user, YearMonth month, BigDecimal totalBudget, LocalDateTime createdAt) {
        this.user = user;
        this.month = month;
        this.totalBudget = totalBudget;
        this.createdAt = createdAt;
    }

    public boolean isExceeded(BigDecimal totalExpense) {
        return totalExpense.compareTo(this.totalBudget) > 0;
    }

}
