#!/bin/bash

docker-compose down

./gradlew clean

./gradlew bootJar

docker-compose build

docker-compose up -d

docker-compose logs -f
