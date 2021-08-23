@echo OFF
call mvn clean package -DskipTests
docker build -t webshop .
pause