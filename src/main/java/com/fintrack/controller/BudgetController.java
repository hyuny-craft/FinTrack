package com.fintrack.controller;

import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.User;
import com.fintrack.dto.BudgetRequest;
import com.fintrack.dto.BudgetUsageResponse;
import com.fintrack.service.BudgetService;
import com.fintrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "월 예산 등록")
    public ResponseEntity<Budget> createBudget(
            @RequestBody @Valid BudgetRequest request) {
        User user = userService.findByEmail(request.getEmail());
        budgetService.create(user, request.getMonth(), request.getTotalBudget());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}/{month}")
    @Operation(summary = "월 예산 조회")
    public ResponseEntity<Budget> getByMonth(@PathVariable String email, @PathVariable YearMonth month) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(budgetService.getByUserAndMonth(user, month));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Budget>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(budgetService.getAllByUser(user));
    }

    @GetMapping("/usage")
    @Operation(summary = "월별 예산 사용율 조회")
    public ResponseEntity<BudgetUsageResponse> getUsage(
            @RequestParam
            @Schema(description = "이메일", example = "user@fintrack.com")
            String email,

            @RequestParam
            @Schema(description = "월별", example = "2025-03")
            YearMonth month
    ) {
        return ResponseEntity.ok(budgetService.getBudgetUsage(email, month));
    }
}
