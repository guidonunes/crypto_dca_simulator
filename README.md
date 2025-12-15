# 📈 Crypto Investment Simulator (Pure Java Edition)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)\
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)\
![JDBC](https://img.shields.io/badge/JDBC-Raw%20SQL-4479A1?style=for-the-badge)

> **A high-performance financial simulation engine built with Vanilla
> Java.**\
> Designed to master low-level database transactions, memory management,
> and design patterns without relying on frameworks like Spring Boot.

------------------------------------------------------------------------

## 📖 About The Project

This is a **Command Line Interface (CLI)** application that simulates
and compares two major investment strategies:

-   **Dollar Cost Averaging (DCA)**
-   **Lump Sum**

Unlike tutorials that rely on in-memory collections, this project uses a
**full persistence layer** with raw **JDBC**, handling:

-   SQL relationships;
-   Foreign key constraints;
-   Transaction control (`COMMIT` / `ROLLBACK`);
-   Manual Object-Relational Mapping (ORM);

------------------------------------------------------------------------

## 🎯 Key Objectives

### ✔️ Manual ORM

Mapping `ResultSet` rows to Java objects, understanding the real cost
behind ORM frameworks.

### ✔️ Transaction Management

Ensuring atomic operations and preserving data integrity with explicit
commit/rollback.

### ✔️ Strategy Pattern

Dynamically switches between investment algorithms at runtime.

### ✔️ Data Cleaning

Pre-processing algorithms to handle anomalies in historical datasets
(e.g., zero or missing prices).

------------------------------------------------------------------------

## ⚙️ Features

### 📊 Strategy Simulation

-   **Lump Sum** → Invest everything on day one\
-   **DCA** → Invest a fixed amount monthly over a chosen period

### 🧠 Real Historical Data

Supports simulations for:\
`BTC`, `ETH`, `XRP`, `SOL`, and indices like `S&P 500` and `NASDAQ`.

### 🗃️ Full CRUD

-   **Create** → Run and save simulations\
-   **Read** → Display formatted tables directly in the console\
-   **Delete** → Remove simulations by ID or wipe history safely with FK
    handling

### 🖥️ Console UI

Clean terminal output using `System.out.printf`.

------------------------------------------------------------------------

## 🛠️ Architecture & Design Patterns

### **1. DAO Pattern (Data Access Object)**

Separates database logic from business logic.

-   `SimulationDao.java` --- handles SQL (`INSERT`, `SELECT`, `DELETE`)\
-   `ConnectionFactory.java` --- Singleton that manages DB connection
    lifecycle

------------------------------------------------------------------------

### **2. Strategy Pattern**

``` java
public interface InvestmentStrategy {
    SimulationResult calculate(String assetPath, double amount);
}
// Implementations:
// LumpSumStrategy
// DcaStrategy
```

Allows seamless switching between simulation types.

------------------------------------------------------------------------

## 🚀 How to Run

### **Prerequisites**

-   Java **17+**
-   MySQL **8.0+**
-   Maven

------------------------------------------------------------------------

## 🗄️ Database Setup

1.  Create the database:

``` sql
CREATE DATABASE crypto_simulator;
```

2.  Run the initialization script (tables: `assets`, `price_history`,
    `simulations`).

3.  Update your DB credentials in `ConnectionFactory.java`:

``` java
private static final String USER = "root";
private static final String PASS = "your_password";
```

------------------------------------------------------------------------

## ▶️ Execution

``` bash
git clone https://github.com/your-username/crypto-simulator.git
cd crypto-simulator

mvn clean install
mvn exec:java -Dexec.mainClass="br.com.dca.App"
```

------------------------------------------------------------------------

## 📜 License

This project is licensed under the **MIT License**.
