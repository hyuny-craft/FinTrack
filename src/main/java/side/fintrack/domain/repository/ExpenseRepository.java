package side.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import side.fintrack.domain.model.Expense;
import side.fintrack.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
