# 🚀 Crypto DCA Simulator
A full-stack investment analysis tool designed to backtest and compare cryptocurrency strategies (Dollar Cost Averaging vs. Lump Sum) using historical market data.


## 🌟 Overview
This project simulates investment scenarios to help users visualize the long-term impact of their strategies. It features a high-precision Java Spring Boot backend for calculations and a responsive Next.js frontend for interactive data visualization.

## ✨ Key Features

* Strategy Comparison: Simulate and compare DCA (Dollar Cost Averaging) vs. Lump Sum investment logic side-by-side.

* Interactive Dashboard: A clean, modern UI built with Next.js and Tailwind CSS to configure simulations and view results instantly.

* Data Visualization: Dynamic line charts (powered by Chart.js) showing portfolio performance over time (Invested Amount vs. Portfolio Value).

* Financial Precision: All backend calculations utilize BigDecimal to prevent floating-point errors, ensuring financial accuracy.

### Scalable Architecture:

Backend: Implements Strategy and Factory design patterns for extensible logic.

Data Transfer: Uses the DTO Pattern (Records) to cleanly separate database entities from API responses.

## 🛠️ Tech Stack

### Backend (API)
☕ Java 21

- 🍃 Spring Boot 3 (Web, Data JPA)

- 🗄️ MySQL (Persistence)

- 🐘 Maven (Dependency Management)

### Frontend (UI)
- ⚛️ Next.js 14 (React Framework)

- 📘 TypeScript (Type safety)

- 🎨 Tailwind CSS (Styling)

- 📊 Chart.js (Data Visualization)

## 🏗️ Architecture

The application follows a layered architecture to separate concerns:

* Controller Layer: Handles REST API endpoints.

* Service Layer: Contains business logic and orchestration.

* Strategy Layer: Encapsulates the specific algorithms for DCA and Lump Sum.

* Repository Layer: Manages data access to MySQL.

* DTO Layer: Defines clean contracts (SimulationRequest, SimulationResponse) for API communication.

## 🚀 Getting Started

### Prerequisites

1. Java 21 SDK

2. Node.js (v18+)

3. MySQL Server

### Backend Setup
```

cd dca-simulator-backend
# Configure your database in src/main/resources/application.properties
./mvnw spring-boot:run
```

### Frontend Setup
```
cd dca-simulator-web
npm install
npm run dev
Access the application at http://localhost:3000.
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License.
