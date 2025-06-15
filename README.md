# 🌿 PolliWeather – Scraper Service

This is the **Scraper microservice** for the PolliWeather system – a distributed artificial intelligence solution for analyzing and forecasting the allergenic hazard of ragweed pollen in Kyiv.

It was created as part of the bachelor’s thesis  
**“Artificial Intelligence System for Trends Analysis in Allergenic Hazard and Allergen Spread in Kyiv City”**  
by Danil Brovko (NTUU "KPI", 2024).

---

## 📚 Table of Contents

- [🚀 Overview](#-overview)
- [⚙️ Technologies](#️-technologies)
- [🌐 Data Sources](#-data-sources)
- [🧠 Functionality](#-functionality)
- [🏗️ Architecture](#-architecture)
- [📦 Project Structure](#-project-structure)
- [🔗 Related Services](#-related-services)

---

## 🚀 Overview

This Java Spring Boot application is responsible for:

- Periodic scraping of **ragweed plant locations** (Ambrosia artemisiifolia) from public sources
- Scraping historical **weather data** (temperature, humidity, pressure)
- Processing and transforming the scraped data
- Storing all structured data into a central **MySQL** database

This data is used by the **Analytics Service** to model time series, analyze allergenic trends, and power the **PolliWeather** web platform.

---

## ⚙️ Technologies

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **RESTful API**
- **MySQL**

---

## 🌐 Data Sources

- 🌱 **Ambrozii.net** – Public map of ragweed sightings contributed by citizens  
  (Requires reverse-engineered scraping due to lack of API)

- ☁️ **rp5.ua** – Historical weather data from the **Zhulyany airport** in Kyiv (3-hour interval)

---

## 🧠 Functionality

- Scrapes ragweed plant records with metadata (ID, coordinates, size, report history)
- Extracts 3-hour weather data (temperature, humidity, pressure)
- Computes **Weather Factor**:  
  **F = (t × P) / H**
  where:  
  • *t* — temperature in °C  
  • *P* — pressure in mmHg  
  • *H* — relative humidity in %
- Maps ragweed coordinates to Kyiv districts
- Exposes data via REST API for the analytics pipeline

---

## 🏗️ Architecture

The PolliWeather system follows a modular architecture built around microservices and a shared data layer. This scraper service operates as **the data ingestion layer**.

<img width="468" alt="image" src="https://github.com/user-attachments/assets/31070479-0e82-473d-9df9-37de8ea5654e" />

---

## 📦 Project Structure

```
pw-scraper-service/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/dbrovko/pwscraperservice/
│   │   │   ├── Application.java
│   │   │   ├── controller/
│   │   │   │   └── RagweedController.java
│   │   │   ├── model/
│   │   │   │   ├── dto/                 # DTOs for ragweed entities
│   │   │   │   ├── pojo/                # POJOs for DB mapping
│   │   │   │   └── converter/           # Converters for parsing and transformation
│   │   │   ├── repository/              # Spring JPA repositories
│   │   │   ├── service/                 # Scraper and business logic
│   │   │   └── exception/              # Custom exceptions
│   └── test/
│       └── PwScraperServiceApplicationTests.java
├── .mvn/                                # Maven wrapper config
└── README.md
```

---

## 🔗 Related Services

- [`polliweather-analytics`](https://github.com/BrovkoD/pw-analytics-service)  
  → Trains AI models (NeuralProphet, AR-Net), performs seasonal predictions, serves insights
