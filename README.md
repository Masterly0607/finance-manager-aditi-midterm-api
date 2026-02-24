# 💰 Personal Finance Manager — Backend API (Spring Boot)

> Secure RESTful Backend API for tracking personal finances (academic mid‑term project).  
> ⚠️ This system does **not** handle real money or integrate with banks.

---

## 📌 Project Overview

The **Personal Finance Manager Backend API** is a secure REST API built with **Spring Boot** that supports:

- Authentication (JWT access token + refresh token via **HttpOnly cookie**)
- Account management (multiple accounts per user)
- Transactions (income, expense)
- Internal transfers (between accounts)
- Dashboard summary (totals, balances, charts-ready data)
- Role-based access control (**USER / ADMIN**)

The focus is on:

- Secure backend architecture
- Financial business logic
- RBAC authorization
- Clean layered architecture

---

## 🎯 Project Objectives

- Build a secure REST API using Spring Boot
- Implement JWT authentication (**Access + Refresh tokens**)
- Manage accounts and balances
- Handle income, expense, and transfer operations
- Provide financial dashboard summaries
- Apply role-based authorization (**USER / ADMIN**)

---

## 🛠️ Technology Stack

### Backend
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- Lombok
- Validation (Jakarta Validation)

### Authentication & Security
- JWT Access Token (Authorization: `Bearer <token>`)
- Refresh Token stored in **HttpOnly Cookie**
- BCrypt password hashing
- Role-based authorization (RBAC)

### Database
- PostgreSQL
- JPA / Hibernate ORM

### Deployment
- Backend: Render

---

## 📂 Backend Project Structure

```bash
financemanager-backend/
├── src/main/java/com/.../
│   ├── auth/           # register/login/refresh/logout/me
│   ├── security/       # JWT filter, token service, security config
│   ├── user/           # user domain + admin controls
│   ├── account/        # account CRUD + balance rules
│   ├── transaction/    # income/expense logic + history
│   ├── transfer/       # internal transfers (source -> destination)
│   ├── dashboard/      # dashboard summary + aggregations
│   └── health/         # health endpoints
└── src/main/resources/
    ├── application.yml
    └── ...
```

### Architecture Pattern

`Controller → Service → Repository → Database`

**Keyword definitions**
- **Controller**: Receives HTTP requests and returns responses.
- **Service**: Contains business logic (rules, calculations).
- **Repository**: Talks to database (JPA queries).
- **DTO**: Data Transfer Object for request/response payloads (avoid exposing entities).
- **RBAC**: Role Based Access Control (permissions by role).

---

## ⚙️ Environment Variables

Create `.env` (or configure Render environment variables):

```env
SPRING_PROFILES_ACTIVE=prod

DB_URL=jdbc:postgresql://<host>:5432/<db>
DB_USERNAME=<username>
DB_PASSWORD=<password>

JWT_SECRET=<long_random_secret>
JWT_ACCESS_EXPIRES_MIN=15
JWT_REFRESH_EXPIRES_DAYS=7

CORS_ALLOWED_ORIGINS=http://localhost:3000,https://<your-frontend-domain>
```

> Notes  
> - `JWT_SECRET` should be strong (long random string).  
> - Refresh token uses cookie: **HttpOnly** (not accessible via JS).

---

## ▶️ Run Locally

### 1) Clone & install
```bash
git clone <repo>
cd financemanager-backend
```

### 2) Configure database
- Create a PostgreSQL database
- Set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`

### 3) Start the app
```bash
./mvnw spring-boot:run
```

App runs at:
- `http://localhost:8080`

---

## 🗄️ Database Design

### ERD (High-level)

```
User (1) ────── (N) Account (1) ────── (N) Transaction
                 │
                 └── Transfers: handled logically by service layer
                     (deduct source, add destination, record history)
```

---

### 1️⃣ User Table

| Field | Type | Description |
|---|---|---|
| id | BIGINT (PK) | Unique identifier |
| email | VARCHAR (UNIQUE) | Login email |
| password_hash | VARCHAR | BCrypt hashed password |
| role | ENUM(USER, ADMIN) | User role |
| is_active | BOOLEAN | Account status |
| created_at | TIMESTAMP | Creation time |

**Relationships**
- One **User** → Many **Accounts**
- One **User** → Many **Transactions** (via accounts)

---

### 2️⃣ Account Table

| Field | Type | Description |
|---|---|---|
| id | BIGINT (PK) | Account ID |
| name | VARCHAR | Account name (Cash, Savings, etc.) |
| balance | DECIMAL | Current balance |
| user_id | BIGINT (FK → User.id) | Owner user |
| created_at | TIMESTAMP | Creation time |

**Relationships**
- Many **Accounts** belong to one **User**
- One **Account** → Many **Transactions**
- One **Account** can be **source** or **destination** in transfers

---

### 3️⃣ Transaction Table

| Field | Type | Description |
|---|---|---|
| id | BIGINT (PK) | Transaction ID |
| type | ENUM(INCOME, EXPENSE, TRANSFER) | Transaction type |
| amount | DECIMAL | Amount |
| note | TEXT | Description / note |
| account_id | BIGINT (FK → Account.id) | Linked account |
| created_at | TIMESTAMP | Creation time |

**Relationships**
- Many **Transactions** belong to one **Account**
- Transfer creates transaction records for tracking history

---

## 🔄 Business Rules

- **Income** → `balance += amount`
- **Expense** → `balance -= amount` (must not go below allowed rules if enforced)
- **Transfer**
  - subtract from **source** account
  - add to **destination** account
  - record transaction history (usually 2 records: OUT + IN, or TRANSFER type as designed)
- **Authorization**
  - Users can only access their own accounts/transactions
  - Admin can access all users and transactions

---

## 🔐 API Endpoints

### Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`
- `GET  /api/auth/me`

### Accounts
- `GET  /api/accounts`
- `POST /api/accounts`
- `PUT  /api/accounts/{id}`

### Transactions
- `GET  /api/transactions`
- `POST /api/transactions/income`
- `POST /api/transactions/expense`

### Transfers
- `POST /api/transfers`

### Dashboard
- `GET  /api/dashboard/summary`

### Admin
- `GET   /api/admin/users`
- `PATCH /api/admin/users/{id}/role`

---

## 🔐 Security Flow (Access + Refresh)

1. User logs in
2. Server validates credentials
3. Server returns:
   - **Access Token (JWT)** in response body
   - **Refresh Token** stored as **HttpOnly cookie**
4. Frontend sends Access Token on protected requests:
   - `Authorization: Bearer <access_token>`
5. When access token expires:
   - Frontend calls `/api/auth/refresh`
   - Backend validates refresh cookie and returns a new access token

---

## 🧾 Standard Response Format (Recommended)

Success:
```json
{
  "success": true,
  "message": "OK",
  "data": {}
}
```

Error:
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": {
    "amount": "Amount must be greater than 0"
  }
}
```

> If your project already has a different response structure, keep that as the source of truth.

---

## ✅ Access Control Summary

| Role | Permissions |
|---|---|
| USER | Can access **only their own** accounts, transactions, dashboard |
| ADMIN | Can access all user data + admin endpoints (user management) |

---

## 👥 Task Division (Team)

| Member | Responsibility | Description |
|---|---|---|
| **Masterly** | Authentication & Security | JWT implementation, Spring Security config, login/register, route protection |
| **Raksa** | Account Module | Account CRUD, balance management logic |
| **Chhai** | Transactions & Transfers | Income/Expense logic, transfer implementation, financial calculations |
| **Narin** | Admin Module | User management, role toggle (USER ↔ ADMIN), admin authorization rules |

---

## 🎯 Expected Outcome

- Secure JWT authentication (access + refresh)
- Accurate financial calculations and balance updates
- Proper role-based access control
- Clean layered architecture
- Academic-quality backend submission

---

## 📖 Conclusion

This backend demonstrates secure REST API development using Spring Boot and PostgreSQL, including authentication, financial logic, entity relationships, and RBAC authorization—aligned for an academic mid‑term submission.
