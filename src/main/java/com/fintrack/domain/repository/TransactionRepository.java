package com.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fintrack.domain.model.Transaction;
import com.fintrack.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
