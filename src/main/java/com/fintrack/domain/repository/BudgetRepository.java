package com.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.User;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndMonth(User user, YearMonth month);

    List<Budget> findAllByUser(User user);
}
