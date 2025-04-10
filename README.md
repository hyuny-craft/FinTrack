# FinTrack

FinTrack는 개인 금융 관리를 위한 서비스로, 지출 추적, 예산 설정, 알림 관리, 외부 이체 내역 시각화 등의 기능을 제공합니다.  
이 프로젝트는 **DDD + 헥사고날 아키텍처** 기반으로 구성되었으며,  
최신 Spring Boot 환경(Java 21, Spring Boot 3.4.3)을 활용해 구현되었습니다.

---

## 📦 기술 스택

- Java 21
- Spring Boot 3.4.3
- Spring Security (JWT + OAuth2)
- JPA (Hibernate)
- PostgreSQL (개발 중 H2 사용)
- Swagger (Springdoc OpenAPI 2.8.5)
- Gradle
- OAuth2 (Google)

---

## 📁 프로젝트 구조 (레이어드 아키텍처)

추후 헥사고날 반영 예정

```
com.fintrack
├── application       # 서비스 로직
├── controller        # API 컨트롤러
├── domain
│   ├── model         # 엔티티, 도메인 객체
│   └── repository    # JPA Repository 인터페이스
├── dto               # Request/Response DTO
├── security          # JWT, 필터, SecurityConfig
└── util              # 유틸, 헬퍼 등
```

---

## ✨ 주요 기능 요약

### ✅ 사용자 (User)

- 일반 회원가입 (`/api/users/register`)
- 소셜 로그인시 자동 등록
- 이메일 중복 확인 및 이메일 기반 조회

### ✅ 인증/인가 (Auth)

- 로그인 (JWT 토큰 발급)
- JWT 인증 필터, 토큰 유효성 검사
- OAuth2 연동 (구글)
- OAuth2 SuccessHandler → JWT 자동 발급 및 응답

### ✅ 지출 (Expense)

- 지출 등록 (`POST /api/expenses`)
- 기간별/사용자별 조회 (`GET /api/expenses` with query param)

### ✅ 알림 (Notification)

- 알림 전송 (`POST /api/notifications`)
- 사용자별 알림 조회 (`GET /api/notifications/{email}`)

### ✅ 외부 이체 내역 (TransferHistory)

- 외부 API 호출 기반 이체 내역 수집 (준비 중)
- 자기자금 간 이체 필터링 및 시각화

---

## 🔒 보안

- 비로그인 사용자 접근 제한 (일부 API 제외)
- dev 환경에서만 Swagger UI 접근 허용
- `.env` 파일을 통해 OAuth2 client-secret 관리

---

## 🚀 실행 방법

추후 제공

---

## 🧪 테스트

- H2 DB 기반 단위 테스트
- Swagger UI (`http://localhost:8080/swagger-ui/index.html`)로 직접 API 테스트 가능 (dev 환경 한정)

---

## 🛠 향후 계획

- 예산 도메인 기능 구현
- 알림 스케줄링 (매월 고정지출 알림 등)
- OAuth2 인증 흐름 개선 및 보안 강화
- 사용자 설정 기능 추가
- 데이터 통계 대시보드

---

## 🙌 기여

PR 환영합니다. 사전 이슈 등록 후 커밋 부탁드립니다.
