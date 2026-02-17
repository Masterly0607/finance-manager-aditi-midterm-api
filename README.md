# 💰 Personal Finance Manager Web Application

## 📌 Project Overview

The **Personal Finance Manager Web Application** is a full-stack web application that helps users track personal finances in a simple and secure way. Users can record income and expenses, manage multiple accounts, and view financial summaries. An Admin role is included to simulate system supervision.

This project is developed for **mid-term academic purposes** and focuses on backend security, financial logic, and frontend integration without involving real money.

---

## 🎯 Project Objectives

* Build a secure full-stack web application
* Implement JWT authentication (access & refresh tokens)
* Manage income, expenses, and account balances
* Provide dashboard summaries
* Apply role-based access control (User / Admin)

---

## 📦 Project Scope

### ✅ Included

* User registration and login
* JWT authentication
* Role-based access control
* Admin role toggle
* Account management
* Income & expense recording
* Internal transfers
* Dashboard summaries

### ❌ Excluded

* Real money transactions
* Bank or payment gateway integration
* Payments between users

---

## 👥 User Roles

### User

* Manage accounts
* Record income and expenses
* View dashboard and transactions

### Admin

* View all users
* View all transactions
* Toggle user roles

---

## 🛠️ Technology Stack

### Frontend

* Next.js
* shadcn/ui
* Tailwind CSS

### Backend

* Spring Boot
* Spring Security
* JWT Authentication

### Database

* PostgreSQL
* Spring Data JPA

### API Documentation

* Swagger / OpenAPI

### Deployment

* Frontend: Vercel
* Backend: Render
* Database: Neon (PostgreSQL)

---

## 📁 Project Structure

### Backend (Spring Boot)

```
financemanager-backend/
├─ auth/
│  ├─ dto/
│  │  ├─ RegisterRequest.java
│  │  ├─ LoginRequest.java
│  │  ├─ AuthResponse.java
│  │  └─ MeResponse.java
│  ├─ AuthController.java
│  └─ AuthService.java
├─ security/
│  ├─ JwtService.java
│  ├─ JwtAuthFilter.java
│  ├─ SecurityConfig.java
│  └─ UserPrincipal.java
├─ user/
│  ├─ dto/
│  │  ├─ UserResponse.java
│  │  ├─ AdminUserResponse.java
│  │  └─ UpdateRoleRequest.java
│  ├─ mapper/
│  │  └─ UserMapper.java
│  ├─ User.java
│  ├─ Role.java
│  ├─ UserRepository.java
│  ├─ UserController.java
│  └─ UserService.java
├─ account/
│  ├─ dto/
│  │  ├─ CreateAccountRequest.java
│  │  ├─ UpdateAccountRequest.java
│  │  └─ AccountResponse.java
│  ├─ mapper/
│  │  └─ AccountMapper.java
│  ├─ Account.java
│  ├─ AccountRepository.java
│  ├─ AccountController.java
│  └─ AccountService.java
├─ transaction/
│  ├─ dto/
│  │  ├─ CreateIncomeRequest.java
│  │  ├─ CreateExpenseRequest.java
│  │  ├─ TransactionResponse.java
│  │  └─ TransactionQuery.java
│  ├─ mapper/
│  │  └─ TransactionMapper.java
│  ├─ Transaction.java
│  ├─ TransactionRepository.java
│  ├─ TransactionController.java
│  └─ TransactionService.java
├─ transfer/
│  ├─ dto/
│  │  ├─ TransferRequest.java
│  │  └─ TransferResponse.java
│  ├─ mapper/
│  │  └─ TransferMapper.java
│  ├─ TransferController.java
│  └─ TransferService.java
├─ dashboard/
│  ├─ dto/
│  │  └─ DashboardSummaryResponse.java
│  ├─ DashboardController.java
│  └─ DashboardService.java
└─ health/
   └─ HealthController.java
```

---

### Frontend (Next.js)

```
financemanager-frontend/
├─ app/
│  ├─ (auth)/
│  │  ├─ login/page.tsx
│  │  └─ register/page.tsx
│  ├─ dashboard/page.tsx
│  ├─ accounts/page.tsx
│  ├─ transactions/page.tsx
│  ├─ transfer/page.tsx
│  ├─ admin/
│  │  ├─ users/page.tsx
│  │  └─ transactions/page.tsx
│  └─ layout.tsx
├─ components/
│  ├─ ui/
│  ├─ Navbar.tsx
│  ├─ Sidebar.tsx
│  ├─ AccountCard.tsx
│  ├─ TransactionTable.tsx
│  └─ SummaryCards.tsx
├─ lib/
│  ├─ api.ts
│  ├─ auth.ts
│  └─ validators.ts
└─ middleware.ts
```

---

## 🗄️ Database Design & Relationships

### Entities

#### User

* id (PK)
* email (unique)
* password_hash
* role (USER / ADMIN)
* is_active
* created_at

#### Account

* id (PK)
* name
* balance
* user_id (FK → User.id)
* created_at

#### Transaction

* id (PK)
* type (INCOME / EXPENSE / TRANSFER)
* amount
* note
* account_id (FK → Account.id)
* created_at

#### Transfer (Logical)

* from_account_id (FK → Account.id)
* to_account_id (FK → Account.id)
* amount
* note

### Relationships

* One **User** can have many **Accounts**
* One **Account** can have many **Transactions**
* Transfers move balance between two accounts

### Business Rules

* Income → `balance += amount`
* Expense → `balance -= amount`
* Transfer → deduct from source, add to destination
* Users can only access their own data; Admin can access all

---

## 🔄 Project Flow (How the System Works)

### 1) Register & Login

* User registers and data is saved to the `User` table
* Passwords are stored as hashed values
* Login returns an **access token** and sets a **refresh token** as HttpOnly cookie

### 2) Account Creation

* User creates an account (Cash / Savings)
* Account is linked to the user via `user_id`

### 3) Record Income / Expense

* User submits income or expense
* Transaction is saved to `Transaction` table
* Account balance is updated automatically

### 4) Transfer Between Accounts

* User selects source and destination accounts
* System deducts amount from source and adds to destination
* Transfer is recorded for history tracking

---

## 🔗 Backend API Endpoints

### 🔐 Authentication

| Method | Endpoint             | Description                           |
| ------ | -------------------- | ------------------------------------- |
| POST   | `/api/auth/register` | Register user                         |
| POST   | `/api/auth/login`    | Login (access token + refresh cookie) |
| POST   | `/api/auth/refresh`  | Refresh access token                  |
| POST   | `/api/auth/logout`   | Logout                                |
| GET    | `/api/auth/me`       | Current user info                     |

### 💼 Accounts

| Method | Endpoint             | Description        |
| ------ | -------------------- | ------------------ |
| GET    | `/api/accounts`      | List user accounts |
| POST   | `/api/accounts`      | Create account     |
| PUT    | `/api/accounts/{id}` | Update account     |

### 💰 Transactions

| Method | Endpoint                    | Description       |
| ------ | --------------------------- | ----------------- |
| GET    | `/api/transactions`         | List transactions |
| POST   | `/api/transactions/income`  | Add income        |
| POST   | `/api/transactions/expense` | Add expense       |

### 🔁 Transfers

| Method | Endpoint         | Description               |
| ------ | ---------------- | ------------------------- |
| POST   | `/api/transfers` | Transfer between accounts |

### 📊 Dashboard

| Method | Endpoint                 | Description       |
| ------ | ------------------------ | ----------------- |
| GET    | `/api/dashboard/summary` | Financial summary |

### 🛡️ Admin

| Method | Endpoint                     | Description      |
| ------ | ---------------------------- | ---------------- |
| GET    | `/api/admin/users`           | List users       |
| PATCH  | `/api/admin/users/{id}/role` | Toggle user role |

---

## 👨‍👩‍👧‍👦 Team Contribution

> All members contribute to both backend and frontend development.

| Member   | Backend Tasks             | Frontend Tasks            |
| -------- | ------------------------- | ------------------------- |
| Masterly | Auth & Security           | Login/Register, Auth Flow |
| Raksa    | Accounts & Balance Logic  | Accounts Page             |
| Chhai    | Transactions & Validation | Transactions Page         |
| Narin    | Admin APIs                | Admin Dashboard           |
| Heang    | Dashboard APIs            | Dashboard UI              |

---

## 📅 Timeline

| Week   | Focus                  |
| ------ | ---------------------- |
| Week 1 | Backend Development    |
| Week 2 | Frontend & Integration |

---

## 🎯 Expected Outcome

* Working full-stack application
* Secure authentication system
* Correct financial calculations
* Role-based access control
* Academic-quality midterm submission

---

## 📖 Conclusion

This project demonstrates full-stack development skills, secure backend design, role-based access control, and structured financial data handling using a simplified digital banking model suitable for academic purposes.
