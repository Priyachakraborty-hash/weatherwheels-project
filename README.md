# 🌾 WeatherWheels - Smart Agricultural Equipment Marketplace

## Project Overview
WeatherWheels is an innovative platform that recommends and sells agricultural vehicles and equipment based on real-time weather forecasts and seasonal patterns. Farmers get personalized equipment recommendations based on their location's weather conditions.

## Architecture
- **Backend**: Java Spring Boot Microservices
- **Database**: MySQL
- **Frontend**: React.js with ES6+, Modern CSS
- **Message Broker**: RabbitMQ (for microservice communication)
- **Service Discovery**: Eureka Server

## Microservices Structure
1. **eureka-server** (Port: 8761) - Service Discovery
2. **api-gateway** (Port: 8080) - Single entry point
3. **weather-service** (Port: 8081) - Weather forecasting
4. **product-service** (Port: 8082) - Equipment catalog
5. **recommendation-service** (Port: 8083) - AI recommendations
6. **user-service** (Port: 8084) - User management
7. **order-service** (Port: 8085) - Order processing

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Node.js 16+
- npm or yarn

### Database Setup
1. Install MySQL if not already installed
2. Create database:
```sql
CREATE DATABASE weatherwheels_db;
CREATE DATABASE weather_db;
CREATE DATABASE product_db;
CREATE DATABASE user_db;
CREATE DATABASE order_db;
```

### Backend Setup
1. Navigate to each microservice directory
2. Run `mvn clean install` for each service
3. Start services in this order:
   - eureka-server: `mvn spring-boot:run`
   - Other services: `mvn spring-boot:run`

### Frontend Setup
1. Navigate to `frontend` directory
2. Install dependencies: `npm install`
3. Start development server: `npm start`
4. Access at: http://localhost:3000

## Features
- Real-time weather integration
- Smart equipment recommendations
- Seasonal planning tools
- Equipment comparison
- Order tracking
- Farmer community reviews

## API Endpoints
- Gateway URL: http://localhost:8080
- Eureka Dashboard: http://localhost:8761
