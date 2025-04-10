package com.fintrack.domain.repository;

import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    List<Budget> findAllByUser(User user);

    @Query("""
            SELECT COALESCE(b.totalBudget,0)
            FROM Budget b
            where b.user.email = :email
            AND b.date BETWEEN :start AND :end
            """)
    BigDecimal findByUserEmailAndDateBetween(String email, LocalDate start, LocalDate end);

}
