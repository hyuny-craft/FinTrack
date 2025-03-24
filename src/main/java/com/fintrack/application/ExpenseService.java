package com.fintrack.application;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Transactional
    public Expense add(Expense expense) {
        log.info("Adding expense = {}", expense);
        return expenseRepository.save(expense);
    }

    @Transactional(readOnly = true)
    public List<Expense> getByUser(User user) {
        return expenseRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Expense> getByUserAndPeriod(User user, LocalDate start, LocalDate end) {
        return expenseRepository.findByUserAndDateBetween(user, start, end);
    }
}
