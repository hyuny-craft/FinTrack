package com.fintrack.controller;

import com.fintrack.domain.model.User;
import com.fintrack.dto.DashboardResponse;
import com.fintrack.service.DashboardService;
import com.fintrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getSummary(@RequestParam String email,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        User user = userService.findByEmail(email);
        DashboardResponse response = dashboardService.getMonthlySummary(user, month);
        return ResponseEntity.ok(response);

    }
}
