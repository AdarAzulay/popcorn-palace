#!/bin/bash

echo "Building project (skipping tests)..."
mvn clean install -DskipTests

echo "Starting Spring Boot application..."
mvn spring-boot:run
