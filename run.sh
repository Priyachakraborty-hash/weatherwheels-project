#!/bin/bash

echo "🌾 WeatherWheels - Full Stack Application Setup & Run Script 🌾"
echo "================================================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo -e "${YELLOW}Checking prerequisites...${NC}"

if ! command_exists java; then
    echo -e "${RED}❌ Java is not installed. Please install Java 17 or higher.${NC}"
    exit 1
fi

if ! command_exists mvn; then
    echo -e "${RED}❌ Maven is not installed. Please install Maven 3.6+.${NC}"
    exit 1
fi

if ! command_exists node; then
    echo -e "${RED}❌ Node.js is not installed. Please install Node.js 16+.${NC}"
    exit 1
fi

if ! command_exists mysql; then
    echo -e "${YELLOW}⚠️  MySQL is not installed. Make sure MySQL is running on port 3306.${NC}"
fi

echo -e "${GREEN}✅ All prerequisites checked!${NC}"

# Create databases
echo -e "${YELLOW}Setting up MySQL databases...${NC}"
echo "Please enter your MySQL root password:"
read -s MYSQL_PASSWORD

mysql -u root -p$MYSQL_PASSWORD <<EOF
CREATE DATABASE IF NOT EXISTS weatherwheels_db;
CREATE DATABASE IF NOT EXISTS weather_db;
CREATE DATABASE IF NOT EXISTS product_db;
CREATE DATABASE IF NOT EXISTS user_db;
CREATE DATABASE IF NOT EXISTS order_db;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
EOF

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Databases created successfully!${NC}"
else
    echo -e "${YELLOW}⚠️  Could not create databases. They may already exist or MySQL is not running.${NC}"
fi

# Build backend services
echo -e "${YELLOW}Building backend microservices...${NC}"
cd backend

# Build parent POM
mvn clean install -DskipTests

# Start Eureka Server
echo -e "${YELLOW}Starting Eureka Server...${NC}"
cd eureka-server
mvn spring-boot:run &
EUREKA_PID=$!
cd ..
sleep 10

# Start API Gateway
echo -e "${YELLOW}Starting API Gateway...${NC}"
cd api-gateway
mvn spring-boot:run &
GATEWAY_PID=$!
cd ..
sleep 5

# Start Weather Service
echo -e "${YELLOW}Starting Weather Service...${NC}"
cd weather-service
mvn spring-boot:run &
WEATHER_PID=$!
cd ..
sleep 5

# Start Product Service
echo -e "${YELLOW}Starting Product Service...${NC}"
cd product-service
mvn spring-boot:run &
PRODUCT_PID=$!
cd ..
sleep 5

cd ..

# Setup and start frontend
echo -e "${YELLOW}Setting up frontend application...${NC}"
cd frontend
npm install

echo -e "${YELLOW}Starting React frontend...${NC}"
npm start &
FRONTEND_PID=$!

echo ""
echo -e "${GREEN}================================================================${NC}"
echo -e "${GREEN}🎉 WeatherWheels Application is now running! 🎉${NC}"
echo -e "${GREEN}================================================================${NC}"
echo ""
echo -e "${YELLOW}Access the application at:${NC}"
echo -e "  ${GREEN}Frontend:${NC} http://localhost:3000"
echo -e "  ${GREEN}API Gateway:${NC} http://localhost:8080"
echo -e "  ${GREEN}Eureka Dashboard:${NC} http://localhost:8761"
echo ""
echo -e "${YELLOW}Microservices running on:${NC}"
echo -e "  Weather Service: http://localhost:8081"
echo -e "  Product Service: http://localhost:8082"
echo ""
echo -e "${YELLOW}Press Ctrl+C to stop all services${NC}"

# Function to cleanup on exit
cleanup() {
    echo -e "${YELLOW}Stopping all services...${NC}"
    kill $EUREKA_PID $GATEWAY_PID $WEATHER_PID $PRODUCT_PID $FRONTEND_PID 2>/dev/null
    echo -e "${GREEN}All services stopped. Goodbye!${NC}"
    exit 0
}

# Set trap to cleanup on Ctrl+C
trap cleanup INT

# Keep script running
wait
