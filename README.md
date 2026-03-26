# HR System

Spring Boot 3.x 練習專案，模擬 HR 員工管理系統。

## 技術棧
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- H2（開發環境記憶體資料庫）
- Redis（快取，Cache Aside pattern）

## 專案結構
```
src/main/java/org/hr/hr/
├── controller/   # API 入口，處理 HTTP 請求
├── service/      # 業務邏輯，包含 Redis cache 處理
├── repository/   # 資料庫存取層
├── entity/       # JPA Entity（對應資料庫 table）
├── model/        # DTO / Response 用的資料模型
├── dto/          # Request 用的資料物件（含驗證規則）
├── exception/    # 統一錯誤處理
└── config/       # Redis 設定
```

## 啟動方式
1. 複製設定檔範本
   cp src/main/resources/application.properties.example src/main/resources/application.properties
2. 啟動專案
   mvn spring-boot:run
3. H2 Console：http://localhost:8080/h2-console
    - JDBC URL：jdbc:h2:mem:testdb
    - 帳號：sa / 密碼：空白

## API

| Method | 路徑 | 說明 |
|--------|------|------|
| GET | /employees | 取得所有員工 |
| GET | /employees/page?page=0&size=10 | 分頁查詢 |
| POST | /employees | 新增員工 |
| PUT | /employees/update | 更新員工 |
| DELETE | /employees/{empId} | 刪除員工 |

## 設計重點

**分頁查詢**：避免一次撈全部資料，降低 DB 與記憶體壓力。

**統一錯誤處理**：`@RestControllerAdvice` 攔截 validation 錯誤，回傳統一格式的 400 錯誤訊息。

**Redis Cache**：`getEmployee` 採用 Cache Aside pattern，先查 Redis，cache miss 才查 DB，並設定 10 分鐘 TTL。Redis 無法連線時降級直接查 DB，不影響服務。