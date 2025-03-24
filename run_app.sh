#!/bin/bash
# run_app.sh - Build the project (skipping tests) and run the Spring Boot application

echo "Building project (skipping tests)..."
mvn clean install -DskipTests

echo "Starting Spring Boot application..."
mvn spring-boot:run
