# 🌾 WeatherWheels - Setup & Running Instructions

## Prerequisites

Before running the application, ensure you have the following installed:

1. **Java 17 or higher**
   - Check: `java --version`
   - Download: https://www.oracle.com/java/technologies/downloads/

2. **Maven 3.6+**
   - Check: `mvn --version`
   - Download: https://maven.apache.org/download.cgi

3. **MySQL 8.0+**
   - Check: `mysql --version`
   - Download: https://dev.mysql.com/downloads/mysql/
   - Default credentials: username: `root`, password: `password`

4. **Node.js 16+ and npm**
   - Check: `node --version` and `npm --version`
   - Download: https://nodejs.org/

## Quick Start (Automated)

### For Linux/Mac:
```bash
chmod +x run.sh
./run.sh
```

### For Windows:
```batch
run.bat
```

## Manual Setup Instructions

### Step 1: Database Setup

1. Start MySQL service
2. Login to MySQL:
   ```bash
   mysql -u root -p
   ```
3. Create databases:
   ```sql
   CREATE DATABASE IF NOT EXISTS weatherwheels_db;
   CREATE DATABASE IF NOT EXISTS weather_db;
   CREATE DATABASE IF NOT EXISTS product_db;
   CREATE DATABASE IF NOT EXISTS user_db;
   CREATE DATABASE IF NOT EXISTS order_db;
   ```
4. Exit MySQL:
   ```sql
   exit;
   ```

### Step 2: Update Database Credentials

If your MySQL credentials are different from the default (root/password), update the following files:
- `backend/weather-service/src/main/resources/application.yml`
- `backend/product-service/src/main/resources/application.yml`

Change these lines:
```yaml
spring:
  datasource:
    username: your_username
    password: your_password
```

### Step 3: Build Backend Services

1. Navigate to backend directory:
   ```bash
   cd backend
   ```

2. Build all services:
   ```bash
   mvn clean install -DskipTests
   ```

### Step 4: Start Microservices (in separate terminals)

**Terminal 1 - Eureka Server:**
```bash
cd backend/eureka-server
mvn spring-boot:run
```
Wait for the message: "Started EurekaServerApplication"

**Terminal 2 - API Gateway:**
```bash
cd backend/api-gateway
mvn spring-boot:run
```

**Terminal 3 - Weather Service:**
```bash
cd backend/weather-service
mvn spring-boot:run
```

**Terminal 4 - Product Service:**
```bash
cd backend/product-service
mvn spring-boot:run
```

### Step 5: Setup and Start Frontend

**Terminal 5 - React Frontend:**
```bash
cd frontend
npm install
npm start
```

## Accessing the Application

Once all services are running, access:

- **Frontend Application**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **Weather API**: http://localhost:8081/api/weather
- **Products API**: http://localhost:8082/api/products

## API Endpoints

### Weather Service (via Gateway)
- `GET /api/weather/current/{location}` - Get current weather
- `GET /api/weather/forecast/{location}?days=7` - Get weather forecast
- `GET /api/weather/seasonal/{location}/{season}` - Get seasonal weather

### Product Service (via Gateway)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/season/{season}` - Get products by season
- `GET /api/products/weather/{condition}` - Get products by weather condition

## Features Demo

1. **Browse Products**: View all agricultural equipment with bright, colorful cards
2. **Weather Integration**: See real-time weather data and recommendations
3. **Category Filter**: Filter products by category (Tractors, Harvesters, etc.)
4. **Seasonal Recommendations**: Products tagged with suitable seasons
5. **Shopping Cart**: Add products to cart with animated interactions
6. **Responsive Design**: Works on desktop and mobile devices

## Troubleshooting

### Port Already in Use
If you get a "port already in use" error, check and kill the process:
```bash
# Linux/Mac
lsof -i :PORT_NUMBER
kill -9 PID

# Windows
netstat -ano | findstr :PORT_NUMBER
taskkill /PID PID /F
```

### MySQL Connection Error
- Ensure MySQL is running
- Check credentials in application.yml files
- Verify databases are created

### Frontend Not Loading
- Clear browser cache
- Check if backend services are running
- Verify CORS settings in API Gateway

### Service Registration Issues
- Wait for Eureka Server to fully start (30 seconds)
- Check Eureka Dashboard at http://localhost:8761
- Ensure all services show as "UP"

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.1.5
- Spring Cloud (Eureka, Gateway)
- MySQL 8.0
- JPA/Hibernate
- Maven

### Frontend
- React 18
- JavaScript ES6+
- Framer Motion (animations)
- Axios (API calls)
- React Icons
- Modern CSS with CSS Variables

### Architecture
- Microservices Architecture
- Service Discovery (Eureka)
- API Gateway Pattern
- RESTful APIs
- Responsive Web Design

## Project Structure
```
weatherwheels-project/
├── backend/
│   ├── eureka-server/
│   ├── api-gateway/
│   ├── weather-service/
│   ├── product-service/
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   └── package.json
├── README.md
├── run.sh (Linux/Mac)
└── run.bat (Windows)
```

## Stopping the Application

### Linux/Mac:
Press `Ctrl+C` in each terminal window

### Windows:
Close all command prompt windows

### Or kill all Java processes:
```bash
# Linux/Mac
pkill -f spring-boot

# Windows
taskkill /F /IM java.exe
```

## Support

For any issues or questions about the submission:
- Check the console logs for error messages
- Ensure all prerequisites are installed
- Verify database connectivity
- Review the architecture diagram in README.md

## Presentation Tips

1. Start with showing the Eureka Dashboard to demonstrate microservices
2. Show the bright, colorful UI with animations
3. Demonstrate weather-based recommendations
4. Add products to cart to show interactivity
5. Filter by categories to show dynamic updates
6. Open Network tab to show API calls between services
7. Highlight the responsive design on different screen sizes

Good luck with your submission! 🌾🚜
