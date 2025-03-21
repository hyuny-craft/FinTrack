package side.fintrack.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import side.fintrack.domain.model.Budget;
import side.fintrack.domain.model.User;
import side.fintrack.domain.repository.BudgetRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;

    @Transactional
    public Budget create(User user, YearMonth month, BigDecimal totalBudget) {
        Budget budget = new Budget(user, month, totalBudget, LocalDateTime.now());
        return budgetRepository.save(budget);
    }

    public Budget getByUserAndMonth(User user, YearMonth month) {
        return budgetRepository.findByUserAndMonth(user, month).orElseThrow(() -> new IllegalArgumentException("Month not found"));
    }

    public List<Budget> getAllByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }


}
