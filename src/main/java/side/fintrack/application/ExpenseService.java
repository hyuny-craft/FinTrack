package side.fintrack.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.fintrack.domain.model.Expense;
import side.fintrack.domain.model.ExpenseCategory;
import side.fintrack.domain.model.User;
import side.fintrack.domain.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Transactional
    public Expense add(User user, ExpenseCategory category, BigDecimal amount, String description, LocalDate date) {
        Expense expense = new Expense(user, category, amount, description, date);
        return expenseRepository.save(expense);
    }

    public List<Expense> getByUser(User user) {
        return expenseRepository.findAllByUser(user);
    }

    public List<Expense> getByUserAndPeriod(User user, LocalDate start, LocalDate end) {
        return expenseRepository.findByUserAndDateBetween(user, start, end);
    }
}
