package com.fintrack.domain.repository;

import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndMonth(User user, YearMonth month);

    List<Budget> findAllByUser(User user);

    @Query("SELECT COALESCE(b.totalBudget,0) FROM Budget b where b.user.email = :email AND b.month = :month")
    BigDecimal findByUserEmailAndMonth(@Param("email") String email, @Param("month") YearMonth yearMonth);

}
