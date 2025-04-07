package com.fintrack.domain.repository;

import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    List<TransferHistory> findByUserAndDateBetween(User user, LocalDate from, LocalDate to);

    List<TransferHistory> findByUser(User user);
}
