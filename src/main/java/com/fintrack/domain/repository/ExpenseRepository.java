package com.fintrack.domain.repository;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;
import com.fintrack.dto.CategoryRatioResponse;
import com.fintrack.dto.ExpenseMonthlyResponse;
import com.fintrack.dto.WeeklyExpenseTrendResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e where e.user = :user AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    BigDecimal findTotalAmountByUserAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND FUNCTION('YEAR', e.date) = :year AND FUNCTION('MONTH', e.date) = :month")
    List<Expense> findByUserAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);


    BigDecimal sumByUserEmailAndDateBetween(String email, LocalDate startDate, LocalDate endDate);


    List<ExpenseMonthlyResponse.ExpenseMonthlyItem> findByUserEmailAndDateBetween(String email, LocalDate startDate, LocalDate endDate);

    List<CategoryRatioResponse.CategoryRatioItem> findByUserEmailAndDateBetween(String email, YearMonth month);


    List<WeeklyExpenseTrendResponse.DailExpense> findExpenseDailyItems(String email, LocalDate start, LocalDate end);

}
