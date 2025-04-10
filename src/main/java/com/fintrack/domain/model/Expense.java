package com.fintrack.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(hidden = true)
@Entity
@NoArgsConstructor
@Getter
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDate date;


    @Builder
    public Expense(User user, ExpenseCategory category, BigDecimal amount, String description, LocalDate date) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }


    public void updateAmount(BigDecimal newAmount) {
        this.amount = newAmount;
    }

    public void updateCategory(ExpenseCategory newCategory) {
        this.category = newCategory;
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }
}
