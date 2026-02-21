# 💰 Personal Finance Manager -- Backend API

---

## 📌 Project Overview

The **Personal Finance Manager Backend API** is a secure RESTful API
built using Spring Boot.\
It manages authentication, accounts, transactions, internal transfers,
and financial dashboard summaries.

This project is developed for academic mid-term purposes and focuses on:

- Secure backend architecture
- Financial business logic
- JWT-based authentication
- Role-based access control (RBAC)
- Clean layered architecture

⚠️ This system does NOT handle real money or real banking integrations.

---

# 🎯 Project Objectives

- Build a secure REST API using Spring Boot
- Implement JWT authentication (Access + Refresh Tokens)
- Manage accounts and balances
- Handle income, expense, and transfer operations
- Provide financial summary dashboard
- Apply role-based authorization (USER / ADMIN)

---

# 🛠️ Technology Stack

## Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- Lombok

## Authentication & Security

- JWT Access Token
- Refresh Token (HttpOnly Cookie)
- BCrypt password hashing
- Role-based authorization

## Database

- PostgreSQL
- JPA / Hibernate ORM

## Deployment

- Backend: Render

---

# 📂 Backend Project Structure

```bash
financemanager-backend/
├── auth/
├── security/
├── user/
├── account/
├── transaction/
├── transfer/
├── dashboard/
└── health/
```

Architecture follows:

Controller → Service → Repository → Database

---

# 🗄️ Database Design

## 1️⃣ User Table

Field Type Description

---

id BIGINT (PK) Unique identifier
email VARCHAR Unique email
password_hash VARCHAR Encrypted password
role ENUM (USER, ADMIN) User role
is_active BOOLEAN Account status
created_at TIMESTAMP Creation time

### Relationship

- One **User** → Many **Accounts**
- One **User** → Many **Transactions (through accounts)**

---

## 2️⃣ Account Table

Field Type Description

---

id BIGINT (PK) Account ID
name VARCHAR Account name (Cash, Savings, etc.)
balance DECIMAL Current balance
user_id BIGINT (FK → User.id) Owner
created_at TIMESTAMP Creation time

### Relationship

- Many **Accounts** belong to One **User**
- One **Account** → Many **Transactions**
- One **Account** can be source or destination of Transfers

---

## 3️⃣ Transaction Table

Field Type Description

---

id BIGINT (PK) Transaction ID
type ENUM (INCOME, EXPENSE, TRANSFER) Transaction type
amount DECIMAL Amount
note TEXT Description
account_id BIGINT (FK → Account.id) Linked account
created_at TIMESTAMP Creation time

### Relationship

- Many **Transactions** belong to One **Account**
- Transfers create transaction records for tracking

---

# 🔗 Entity Relationship Summary

User (1) ──── (N) Account\
Account (1) ──── (N) Transaction

Transfer is handled logically by: - Deducting from source account -
Adding to destination account - Recording transaction history

---

# 🔄 Business Rules

- Income → balance += amount
- Expense → balance -= amount
- Transfer → subtract from source account and add to destination
  account
- Users can only access their own data
- Admin can access all users and transactions

---

# 🔐 API Endpoints

## Authentication

- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/refresh
- POST /api/auth/logout
- GET /api/auth/me

## Accounts

- GET /api/accounts
- POST /api/accounts
- PUT /api/accounts/{id}

## Transactions

- GET /api/transactions
- POST /api/transactions/income
- POST /api/transactions/expense

## Transfers

- POST /api/transfers

## Dashboard

- GET /api/dashboard/summary

## Admin

- GET /api/admin/users
- PATCH /api/admin/users/{id}/role

---

# 🏗️ Security Flow

1.  User logs in
2.  Server validates credentials
3.  Server generates Access Token (JWT)
4.  Refresh token stored as HttpOnly cookie
5.  JWT is validated on every protected request

---

# 🎯 Expected Outcome

- Secure JWT authentication
- Accurate financial calculations
- Proper role-based access control
- Clean architecture implementation
- Academic-quality backend submission

---

# 📖 Conclusion

This backend project demonstrates secure REST API development, financial
data management, entity relationships, and role-based authorization
using Spring Boot and PostgreSQL.
