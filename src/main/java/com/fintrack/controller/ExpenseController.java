package com.fintrack.controller;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;
import com.fintrack.dto.ExpenseListResponse;
import com.fintrack.dto.ExpenseRequest;
import com.fintrack.service.ExpenseService;
import com.fintrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "지출 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지출 등록 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    public ResponseEntity<Expense> save(@RequestBody @Valid ExpenseRequest request) {
        User user = userService.findByEmail(request.getEmail());
        return ResponseEntity.ok(expenseService.save(user, request));
    }

    @GetMapping("/{email}")
    @Operation(summary = "사용자 이메일로 지출 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지출 목록 조회 성공")
    })
    public ResponseEntity<List<ExpenseListResponse>> getAll(@PathVariable String email) {
        User user = userService.findByEmail(email);
        List<Expense> expenses = expenseService.getByUser(user);
        log.info("getAll expenses: {}", expenses);
        List<ExpenseListResponse> response = expenses.stream()

                .map(e -> new ExpenseListResponse(
                        e.getId(),
                        e.getAmount(),
                        e.getDate(),
                        e.getCategory(),
                        e.getDescription()
                ))
                .toList();
        log.info(response.toString());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/range")
    @Operation(summary = "사용자 이메일로 지출 범위 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "범위 지출 목록 조회 성공")
    })
    public ResponseEntity<List<ExpenseListResponse>> getByPeriod(@RequestParam @Valid @Schema(description = "이메일", example = "user@fintrack.com") String email,
                                                                 @RequestParam @Valid @Schema(description = "부터", example = "2024-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                 @RequestParam @Valid @Schema(description = "까지", example = "2024-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        User user = userService.findByEmail(email);
        List<Expense> expenses = expenseService.getByUserAndPeriod(user, from, to);
        List<ExpenseListResponse> response = expenses.stream()
                .map(e -> new ExpenseListResponse(
                        e.getId(),
                        e.getAmount(),
                        e.getDate(),
                        e.getCategory(),
                        e.getDescription()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }
}
