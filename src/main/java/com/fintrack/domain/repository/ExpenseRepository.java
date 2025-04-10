package com.fintrack.domain.repository;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    List<Expense> findByUserEmailAndDateBetween(String email, LocalDate startDate, LocalDate endDate);


    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
                WHERE e.user.email = :email
                AND e.date BETWEEN :startDate AND :endDate
            """)
    BigDecimal sumAmountByUserEmailAndDateBetween(String email, LocalDate startDate, LocalDate endDate);

}
