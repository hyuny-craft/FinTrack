package side.fintrack.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.fintrack.application.BudgetService;
import side.fintrack.application.UserService;
import side.fintrack.domain.model.Budget;
import side.fintrack.domain.model.User;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Budget> create(
            @RequestParam String email,
            @RequestParam YearMonth month,
            @RequestParam BigDecimal amount) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(budgetService.create(user, month, amount));
    }

    @GetMapping("/{email}/{month}")
    public ResponseEntity<Budget> getByMonth(@PathVariable String email, @PathVariable YearMonth month) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(budgetService.getByUserAndMonth(user, month));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Budget>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(budgetService.getAllByUser(user));
    }
}
