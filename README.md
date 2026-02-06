# ⚙️ RealOps Monitor - Backend

## 🔗 Live Demo

**https://realops-monitor.ddns.net**

---

## ✨ 주요 기능

- 📡 **WebSocket** - 2초마다 실시간 메트릭 push
- 💾 **PostgreSQL** - 메트릭 데이터 영구 저장
- ⚡ **Redis** - 현재 메트릭 캐싱 (5초 TTL)
- 🐳 **Docker** - 컨테이너화된 배포
- 📊 **REST API** - 히스토리 및 통계 조회

---

## 🛠️ 기술 스택

| 분류 | 기술 |
|------|------|
| Framework | Spring Boot 4.0.2 |
| Language | Java 21 |
| Database | PostgreSQL 15 |
| Cache | Redis 7 |
| Real-time | WebSocket |
| ORM | JPA (Hibernate) |
| Container | Docker & Docker Compose |

---

## 🚀 시작하기

### 요구사항

- Java 21+
- Docker & Docker Compose
- Maven

### Docker로 실행 (권장)

```bash
# 저장소 클론
git clone https://github.com/Zerojun9403/realops-monitor-backend.git
cd realops-monitor-backend

# Docker Compose로 전체 실행
docker-compose up -d --build
```

PostgreSQL, Redis, Spring Boot 모두 자동 실행됩니다.

### 로컬 개발 환경

```bash
# PostgreSQL, Redis만 Docker로 실행
docker-compose up -d postgres redis

# Spring Boot 실행
./mvnw spring-boot:run
```

---

## ⚙️ 환경 설정

### application.properties

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/realops_monitor
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Docker Compose 환경변수

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/realops_monitor
  - SPRING_DATASOURCE_USERNAME=postgres
  - SPRING_DATASOURCE_PASSWORD=postgres
  - SPRING_DATA_REDIS_HOST=redis
  - SPRING_DATA_REDIS_PORT=6379
```

---

## 📁 프로젝트 구조

```
monitor-backend/
├── src/main/java/com/realops/monitor/
│   ├── entity/
│   │   └── Metrics.java           # 메트릭 엔티티
│   ├── repository/
│   │   └── MetricsRepository.java # JPA 레포지토리
│   ├── service/
│   │   └── MetricsService.java    # 비즈니스 로직
│   ├── controller/
│   │   └── MetricsController.java # REST API
│   ├── handler/
│   │   └── MetricsWebSocketHandler.java  # WebSocket 핸들러
│   ├── config/
│   │   ├── WebSocketConfig.java   # WebSocket 설정
│   │   └── RedisConfig.java       # Redis 설정
│   └── MonitorBackendApplication.java
├── src/main/resources/
│   └── application.properties
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## 🔌 API 문서

### WebSocket

**Endpoint:** `ws://localhost:8080/ws/metrics`

**메시지 형식 (서버 → 클라이언트):**
```json
{
  "cpu": 75.5,
  "memory": 68.2,
  "disk": 45.0,
  "network": 120,
  "timestamp": "2026-02-06T12:00:00"
}
```

### REST API

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/status` | 서버 상태 확인 |
| GET | `/api/metrics` | 현재 메트릭 조회 |
| GET | `/api/metrics/history?period=1h` | 히스토리 조회 |
| GET | `/api/metrics/stats` | 통계 정보 조회 |

### 응답 예시

**GET /api/status**
```json
{
  "status": "online",
  "message": "System operational"
}
```

**GET /api/metrics**
```json
{
  "cpu": 75.5,
  "memory": 68.2,
  "disk": 45.0,
  "network": 120,
  "timestamp": "2026-02-06T12:00:00"
}
```

**GET /api/metrics/history?period=1h**
```json
[
  {"cpu": 70.0, "memory": 65.0, "timestamp": "2026-02-06T11:00:00"},
  {"cpu": 72.5, "memory": 67.0, "timestamp": "2026-02-06T11:01:00"},
  ...
]
```

---

## 🐳 Docker 명령어

```bash
# 전체 서비스 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f backend

# 서비스 중지
docker-compose down

# 이미지 재빌드
docker-compose up -d --build
```

---

## 🗄️ 데이터베이스 스키마

### metrics 테이블

| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT | Primary Key |
| cpu | DOUBLE | CPU 사용률 (%) |
| memory | DOUBLE | 메모리 사용률 (%) |
| disk | DOUBLE | 디스크 사용률 (%) |
| network | DOUBLE | 네트워크 트래픽 (Mbps) |
| timestamp | TIMESTAMP | 측정 시간 |
| created_at | TIMESTAMP | 생성 시간 |

---

## 🏗️ 아키텍처

```
┌─────────────┐     WebSocket      ┌─────────────────┐
│   Client    │◄──────────────────►│  Spring Boot    │
│  (Browser)  │     REST API       │    Backend      │
└─────────────┘                    └────────┬────────┘
                                            │
                           ┌────────────────┼────────────────┐
                           ▼                ▼                ▼
                   ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
                   │  PostgreSQL  │  │    Redis     │  │   Docker     │
                   │   (저장)     │  │   (캐시)     │  │  Compose     │
                   └──────────────┘  └──────────────┘  └──────────────┘
```

---

## 🔗 관련 저장소

- **Frontend**: [realops-monitor](https://github.com/Zerojun9403/realops-monitor)

---

## 📄 라이선스

MIT License - 자유롭게 사용하세요!

---

## 👨‍💻 개발자

- **GitHub**: [@Zerojun9403](https://github.com/Zerojun9403)
- **Experience**: KT 데이터센터 3년 근무 경험

---

⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!
