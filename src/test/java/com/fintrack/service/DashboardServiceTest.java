package com.fintrack.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DashboardServiceTest {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    void redisTemplate_기본동작_확인() {
        redisTemplate.opsForValue().set("test-key", "hello");
        Object value = redisTemplate.opsForValue().get("test-key");
        System.out.println("value = " + value);
        assertThat(value).isEqualTo("hello");

    }

    @Test
    void redisCache_저장_검증() {
        String summary = dashboardService.getSummary(1L);
        System.out.println("summary = " + summary);
        Object cached = redisTemplate.opsForValue().get("summary::1"); // default key format: cacheName::key
        System.out.println("cached = " + cached);
        assertThat(cached).isNotNull();
    }

}