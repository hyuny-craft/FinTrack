package com.fintrack.domain.repository;

import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.User;
import com.fintrack.dto.BalanceFlowResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    List<TransferHistory> findByUserAndDateBetween(User user, LocalDate from, LocalDate to);

    List<TransferHistory> findByUser(User user);

    @Query("""
                SELECT new com.fintrack.dto.BalanceFlowResponse.MonthlyBalanceItem(
                    FUNCTION('to_char', th.date, 'YYYYMM'),
                    SUM(CASE WHEN th.type = 'INCOME' THEN th.amount ELSE 0 END),
                    SUM(CASE WHEN th.type = 'EXPENSE' THEN th.amount ELSE 0 END)
                )
                FROM TransferHistory th
                WHERE th.user.email = :email
                AND th.date BETWEEN :startDate AND :endDate
                GROUP BY FUNCTION('to_char', th.date, 'YYYYMM')
                ORDER BY FUNCTION('to_char', th.date, 'YYYYMM')
            """)
    List<BalanceFlowResponse.MonthlyBalanceItem> findMonthlyIncomeAndExpense(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    default List<BalanceFlowResponse.MonthlyBalanceItem> findMonthlyIncomeAndExpense(String email, YearMonth start, YearMonth end) {
        return findMonthlyIncomeAndExpense(email, start.atDay(1), end.atEndOfMonth());
    }
}
