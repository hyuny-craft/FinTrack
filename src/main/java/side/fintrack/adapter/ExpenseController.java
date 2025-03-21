package side.fintrack.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import side.fintrack.application.ExpenseService;
import side.fintrack.application.UserService;
import side.fintrack.domain.model.Expense;
import side.fintrack.domain.model.ExpenseCategory;
import side.fintrack.domain.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Expense> add(@RequestParam String email, @RequestParam ExpenseCategory category, @RequestParam BigDecimal amount,
                                       @RequestParam(required = false) String description, @RequestParam LocalDate date) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(expenseService.add(user, category, amount, description, date));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Expense>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(expenseService.getByUser(user));
    }

    @GetMapping("/{email}/range")
    public ResponseEntity<List<Expense>> getByPeriod(@PathVariable String email, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(expenseService.getByUserAndPeriod(user, from, to));
    }
}
