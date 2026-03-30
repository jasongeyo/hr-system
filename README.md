# HR System

Spring Boot 3.x 練習專案，模擬 HR 員工管理系統。

## 技術棧
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- H2（開發環境記憶體資料庫）
- Redis（快取，Cache Aside pattern）
- JUnit 5 + Mockito（單元測試）

## 專案結構
```
src/main/java/org/hr/hr/
├── controller/   # API 入口，處理 HTTP 請求
├── service/      # 業務邏輯，包含 Redis cache 處理
├── repository/   # 資料庫存取層（含 Specification 動態查詢）
├── entity/       # JPA Entity（對應資料庫 table）
├── model/        # Response 用的資料模型
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
| GET | /employees/search?deptNo=D01&name=Ja | 條件查詢（可組合） |
| POST | /employees | 新增員工 |
| PUT | /employees/update | 更新員工 |
| DELETE | /employees/{empId} | 刪除員工 |

## 設計重點

### 分頁查詢
用 `PageRequest.of(page, size)` 傳給 Repository，Spring Data JPA 自動產生
`LIMIT / OFFSET` SQL，避免一次撈全部資料造成記憶體壓力。
回傳 `Page` 物件包含總筆數、總頁數，前端可直接用來做分頁元件。

### 動態條件查詢（JPA Specification）
使用 `JpaSpecificationExecutor` 搭配 `Specification`，
支援單一條件或多條件組合查詢，比 if/else 切換查詢方法更靈活易擴充。

### Redis Cache（Cache Aside Pattern）
查詢時先打 Redis，Cache Hit 直接回傳。
Cache Miss 查 DB，結果寫回 Redis 並設 TTL 10 分鐘。
更新資料時先寫 DB 再刪 cache，確保資料一致性。
Redis 無法連線時自動降級直接查 DB，不影響服務。

### 交易管理（@Transactional）
新增、更新、刪除操作加上 `@Transactional`，
確保資料庫操作要嘛全部成功，要嘛全部 rollback，避免資料不一致。

### 統一錯誤處理
`@RestControllerAdvice` 攔截 validation 錯誤，
回傳統一格式的 400 錯誤訊息，方便前端處理。

## 測試
```
mvn test
```
Service 層單元測試使用 Mockito mock Repository，
不依賴外部環境，測試涵蓋 findAll、分頁、單一條件、組合條件查詢。