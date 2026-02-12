# 💰 Personal Finance Manager – Backend (Spring Boot)

---

## 📌 Overview

This is the **Backend API** for the Personal Finance Manager Web Application.

It is built using:

- Spring Boot
- Spring Security
- JWT Authentication (Access + Refresh Tokens)
- PostgreSQL
- Spring Data JPA
- Swagger / OpenAPI

This backend provides secure REST APIs for:

- User Authentication
- Account Management
- Income & Expense Tracking
- Internal Transfers
- Dashboard Financial Summary
- Admin Monitoring

This project was developed for **mid-term academic purposes**.

---

# 🎯 Core Features

## 🔐 Authentication
- User Registration
- User Login
- JWT Access Token generation
- Refresh Token via HttpOnly Cookie
- Logout
- `/api/auth/me` endpoint for current user

## 👤 Role-Based Access Control
- USER role
- ADMIN role
- Admin can:
  - View all users
  - Toggle user roles
  - View all transactions

## 💼 Account Management
- Create Account
- Update Account
- List User Accounts
- Account balance tracking

## 💰 Transactions
- Record Income
- Record Expense
- List Transactions
- Automatic balance update

## 🔁 Transfers
- Transfer between user accounts
- Balance deduction and addition
- Transfer history tracking

## 📊 Dashboard
- Total Balance
- Monthly Income
- Monthly Expense
- Net Calculation

---

# 🏗 Backend Folder Structure

```
financemanager-backend/
├── auth/
│   ├── dto/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   └── MeResponse.java
│   ├── AuthController.java
│   └── AuthService.java
│
├── security/
│   ├── JwtService.java
│   ├── JwtAuthFilter.java
│   ├── SecurityConfig.java
│   └── UserPrincipal.java
│
├── user/
│   ├── dto/
│   │   ├── UserResponse.java
│   │   ├── AdminUserResponse.java
│   │   └── UpdateRoleRequest.java
│   ├── mapper/UserMapper.java
│   ├── User.java
│   ├── Role.java
│   ├── UserRepository.java
│   ├── UserController.java
│   └── UserService.java
│
├── account/
│   ├── dto/
│   │   ├── CreateAccountRequest.java
│   │   ├── UpdateAccountRequest.java
│   │   └── AccountResponse.java
│   ├── mapper/AccountMapper.java
│   ├── Account.java
│   ├── AccountRepository.java
│   ├── AccountController.java
│   └── AccountService.java
│
├── transaction/
│   ├── dto/
│   │   ├── CreateIncomeRequest.java
│   │   ├── CreateExpenseRequest.java
│   │   ├── TransactionResponse.java
│   │   └── TransactionQuery.java
│   ├── mapper/TransactionMapper.java
│   ├── Transaction.java
│   ├── TransactionRepository.java
│   ├── TransactionController.java
│   └── TransactionService.java
│
├── transfer/
│   ├── dto/
│   │   ├── TransferRequest.java
│   │   └── TransferResponse.java
│   ├── mapper/TransferMapper.java
│   ├── TransferController.java
│   └── TransferService.java
│
├── dashboard/
│   ├── dto/DashboardSummaryResponse.java
│   ├── DashboardController.java
│   └── DashboardService.java
│
└── health/
    └── HealthController.java
```

---

# 🗄️ Database Design

## User
- id (PK)
- email (unique)
- password_hash
- role (USER / ADMIN)
- is_active
- created_at

## Account
- id (PK)
- name
- balance
- user_id (FK → User.id)
- created_at

## Transaction
- id (PK)
- type (INCOME / EXPENSE / TRANSFER)
- amount
- note
- account_id (FK → Account.id)
- created_at

## Relationships
- One User → Many Accounts
- One Account → Many Transactions

---

# 🔗 API Endpoints

## 🔐 Authentication

| Method | Endpoint |
|--------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |
| POST | /api/auth/refresh |
| POST | /api/auth/logout |
| GET  | /api/auth/me |

## 💼 Accounts

| Method | Endpoint |
|--------|----------|
| GET | /api/accounts |
| POST | /api/accounts |
| PUT | /api/accounts/{id} |

## 💰 Transactions

| Method | Endpoint |
|--------|----------|
| GET | /api/transactions |
| POST | /api/transactions/income |
| POST | /api/transactions/expense |

## 🔁 Transfers

| Method | Endpoint |
|--------|----------|
| POST | /api/transfers |

## 📊 Dashboard

| Method | Endpoint |
|--------|----------|
| GET | /api/dashboard/summary |

## 🛡 Admin

| Method | Endpoint |
|--------|----------|
| GET | /api/admin/users |
| PATCH | /api/admin/users/{id}/role |

---

# 🛠 Run Backend

## 1️⃣ Configure Database

Update `application.yml`:

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/finance_manager
    username: postgres
    password: your_password
```

## 2️⃣ Run Application

```
./mvnw spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

---

# 👨‍👩‍👧‍👦 Team Contribution (Backend)

| Member   | Responsibility |
|----------|---------------|
| Masterly | Auth & Security Configuration |
| Raksa    | Account Logic & Balance Management |
| Chhai    | Transaction Module |
| Heang    | Dashboard Logic |
| Narin    | Admin APIs & Role Management |

---

# 🎯 Academic Purpose

This backend demonstrates:

- Secure JWT authentication
- Role-based access control
- Clean layered architecture
- Proper DTO separation
- Service & repository pattern usage
- Real-world REST API design

---

# 📄 License

Academic project – non-commercial use.
