package com.fintrack.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DashboardServiceTest {

    private DashboardService dashboardService;

    @Test
    void 호출할때마다_결과가_달라진다() {
        String first = dashboardService.getSummary(1L);
        String second = dashboardService.getSummary(1L);

        Assertions.assertThat(first).isEqualTo(second);
    }

}