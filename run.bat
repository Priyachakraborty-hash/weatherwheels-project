@echo off
echo ===============================================================
echo  WeatherWheels - Full Stack Application Setup and Run Script
echo ===============================================================
echo.

:: Check prerequisites
echo Checking prerequisites...

where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed. Please install Maven 3.6+.
    pause
    exit /b 1
)

where node >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed. Please install Node.js 16+.
    pause
    exit /b 1
)

echo All prerequisites checked!
echo.

:: Create databases
echo Setting up MySQL databases...
echo Please make sure MySQL is running on port 3306
echo Enter your MySQL root password:
set /p MYSQL_PASSWORD=

mysql -u root -p%MYSQL_PASSWORD% -e "CREATE DATABASE IF NOT EXISTS weatherwheels_db; CREATE DATABASE IF NOT EXISTS weather_db; CREATE DATABASE IF NOT EXISTS product_db; CREATE DATABASE IF NOT EXISTS user_db; CREATE DATABASE IF NOT EXISTS order_db;"

if %errorlevel% equ 0 (
    echo Databases created successfully!
) else (
    echo Warning: Could not create databases. They may already exist or MySQL is not running.
)

echo.
echo Building backend microservices...
cd backend

:: Build parent POM
call mvn clean install -DskipTests

:: Start services in new windows
echo Starting Eureka Server...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Weather Service...
start "Weather Service" cmd /k "cd weather-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Product Service...
start "Product Service" cmd /k "cd product-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

cd ..

:: Setup and start frontend
echo.
echo Setting up frontend application...
cd frontend
call npm install

echo Starting React frontend...
start "Frontend" cmd /k "npm start"

cd ..

echo.
echo ===============================================================
echo    WeatherWheels Application is now running!
echo ===============================================================
echo.
echo Access the application at:
echo   Frontend: http://localhost:3000
echo   API Gateway: http://localhost:8080
echo   Eureka Dashboard: http://localhost:8761
echo.
echo Microservices running on:
echo   Weather Service: http://localhost:8081
echo   Product Service: http://localhost:8082
echo.
echo Close all command windows to stop the services.
echo.
pause
