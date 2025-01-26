#!/bin/sh

echo ">>> Building..."
./gradlew clean bootJar

echo ">>> Building Docker image"
docker build . -t demo/social-network:0.0.1-SNAPSHOT