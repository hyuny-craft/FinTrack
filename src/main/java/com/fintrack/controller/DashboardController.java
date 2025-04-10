package com.fintrack.controller;

import com.fintrack.dto.DashboardResponse;
import com.fintrack.dto.ExpenseMonthlyResponse;
import com.fintrack.dto.TotalExpenseResponse;
import com.fintrack.dto.WeeklyExpenseTrendResponse;
import com.fintrack.service.DashboardService;
import com.fintrack.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "대시보드", description = "통계 관련 API")
public class DashboardController {
    private final DashboardService dashboardService;
    private final ExpenseService expenseService;

    @Operation(summary = "대시보드 요약 정보 조회", description = "월별 지출, 예산 대비 지출률, 카테고리별 비율 등을 포함")
    @GetMapping
    public ResponseEntity<DashboardResponse> getSummary(@RequestParam String email,
                                                        @RequestParam
                                                        @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        DashboardResponse response = dashboardService.getDashboardSummary(email, yearMonth);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "총 지출 조회", description = "지정한 연도와 월에 대한 총 지출을 반환합니다.")
    @GetMapping("/total-expense")
    public ResponseEntity<TotalExpenseResponse> getTotalExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(expenseService.getTotalExpense(userDetails.getUsername(), yearMonth));
    }

    @Operation(summary = "월별 지출 합계 조회")
    @GetMapping("/monthly-expense")
    public ResponseEntity<ExpenseMonthlyResponse> getMonthlyExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "조회할 월 (yyyy-MM 형식)")
            @RequestParam("yearMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        return ResponseEntity.ok(expenseService.calculateMonthlyExpense(userDetails.getUsername(), yearMonth));
    }

    @Operation(summary = "최근 7일간 지출 추이 조회")
    @GetMapping("/weekly-expense-trend")
    public ResponseEntity<WeeklyExpenseTrendResponse> getWeeklyExpenseTrend(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(expenseService.getWeeklyTrend(userDetails.getUsername()));
    }

//    @Operation(summary = "총 잔액 흐름 조회", description = "월별 입출금 차이를 통해 총 잔액 흐름을 확인")
//    @GetMapping("/balance-flow")
//    public ResponseEntity<BalanceFlowResponse> getBalanceFlow(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth start,
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth end) {
//        return ResponseEntity.ok(balanceService.getBalanceFlow(userDetails.getUsername(), start, end));
//    }
}
