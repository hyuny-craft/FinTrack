package com.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
