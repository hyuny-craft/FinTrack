package side.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import side.fintrack.domain.model.Budget;
import side.fintrack.domain.model.User;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndMonth(User user, YearMonth month);

    List<Budget> findAllByUser(User user);
}
