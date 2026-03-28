# Game đoán số — API Spring Boot

Dự án REST API cho bài test Java (Inmobi): đăng ký/đăng nhập JWT, đoán số 1–5, bảng xếp hạng, mua lượt.

## Môi trường

- **JDK** 17+
- **MySQL** 8 (hoặc chỉ cần **Docker** để chạy full stack)

## Chạy nhanh

### 1) MySQL sẵn trên máy

Tạo DB (hoặc dùng script `guessing_game_inmobitest.sql`), cấu hình biến môi trường nếu khác mặc định:

```bash
./mvnw spring-boot:run
```

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

API: `http://localhost:8080`

### 2) Docker (MySQL + app)

```bash
docker compose up --build
```

Mặc định: app `8080`, MySQL map ra host (xem `docker-compose.yml`). Đổi port nếu trùng:

```powershell
$env:APP_HOST_PORT="8080"
$env:MYSQL_HOST_PORT="3307"
docker compose up --build
```

Dừng: `docker compose down` — xóa cả volume DB: `docker compose down -v`

## Biến môi trường (tùy chọn)

| Biến | Ý nghĩa | Mặc định gợi ý |
|------|---------|----------------|
| `SERVER_PORT` | Cổng HTTP | `8080` |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` | Kết nối MySQL | `localhost`, `3306`, `guessing_game`, … |
| `JWT_SECRET`, `JWT_EXPIRATION_MS` | Ký JWT | đặt `JWT_SECRET` mạnh khi deploy |
| `GAME_WIN_PROBABILITY` | Xác suất “thắng” (server khớp số bạn đoán) | `0.05` (5%) |
| `GAME_PURCHASE_TURNS` | Số lượt mỗi lần `POST /buy-turns` | `5` |

## Đăng nhập và gọi API có bảo vệ

1. **Đăng ký** `POST /register` — body JSON: `username`, `password` (khi lưu, `email` được gán bằng `username`).
2. **Đăng nhập** `POST /login` — nhận `token` (JWT).
3. Các endpoint khác: header `Authorization: Bearer <token>`.

Ví dụ (sau khi có token):

```bash
curl -s -X POST http://localhost:8080/guess ^
  -H "Authorization: Bearer TOKEN" ^
  -H "Content-Type: application/json" ^
  -d "{\"number\":3}"
```

(Bash/Linux/macOS: bỏ `^`, xuống dòng bằng `\`.)

**Swagger UI:** `http://localhost:8080/swagger-ui.html` — có thể “Authorize” bằng Bearer token rồi thử trực tiếp.

## Endpoint chính

| Method | Đường dẫn | Ghi chú |
|--------|-----------|---------|
| POST | `/register`, `/login` | Công khai |
| POST | `/guess` | Body `{"number":1..5}`; trừ 1 lượt; đúng +1 điểm (logic xác suất theo cấu hình) |
| POST | `/buy-turns` | Cộng lượt (mặc định +5) |
| GET | `/leaderboard` | Top 10 theo điểm |
| GET | `/me` | `username`, `email`, `score`, `turns` |

## Build & test

```bash
./mvnw clean test
./mvnw clean package
```

Profile test dùng H2 in-memory (không cần MySQL).

## Cơ sở dữ liệu

- Script mẫu: `guessing_game_inmobitest.sql`
- User mẫu (nếu có trong script): ví dụ `dat` / `123456`

