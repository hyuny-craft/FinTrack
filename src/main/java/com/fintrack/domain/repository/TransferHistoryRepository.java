package com.fintrack.domain.repository;

import com.fintrack.domain.model.TransferHistory;
import com.fintrack.domain.model.User;
import com.fintrack.dto.MonthlyBalanceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    List<TransferHistory> findByUser(User user);

    @Query("""
                SELECT new com.fintrack.dto.MonthlyBalanceItem(
                    CAST(FORMATDATETIME(th.date, 'YYYY-MM') AS string),
                    CAST(SUM(CASE WHEN th.type = 'INCOME' THEN th.amount ELSE 0 END ) as bigdecimal),
                    CAST(SUM(CASE WHEN th.type = 'EXPENSE' THEN th.amount ELSE 0 END ) AS bigdecimal),
                    CAST(SUM(CASE WHEN th.type = 'INCOME' THEN th.amount ELSE 0 END) -
                    SUM(CASE WHEN th.type = 'EXPENSE' THEN th.amount ELSE 0 END ) AS bigdecimal  )
            
                )
                FROM TransferHistory th
                WHERE th.user.email = :email
                AND th.date BETWEEN :startDate AND :endDate
                GROUP BY YEAR(th.date), MONTH(th.date)
                ORDER BY YEAR(th.date), MONTH(th.date)
            """)
    List<MonthlyBalanceItem> findMonthlyBalanceItemsByUserEmailAndDateBetween(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}
