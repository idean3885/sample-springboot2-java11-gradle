#!/bin/bash

BUILD_NAME=sample-springboot2-jdk11-gradle
SERVER_PORT=8080
ACTUATOR_PORT=8081

docker rmi $BUILD_NAME
docker build -t $BUILD_NAME .

# foreground -> script exit = docker process stop
docker run --rm -p $SERVER_PORT:8080 -p $ACTUATOR_PORT:8081 --name $BUILD_NAME $BUILD_NAME

# for docker data remaining local
#docker run --rm -p $SERVER_PORT:8080 -p $ACTUATOR_PORT:8081 --name $BUILD_NAME -v ./:/app $BUILD_NAME

# background -> must docker stop manually for process exit
# docker run -d --rm -p $SERVER_PORT:8080 -p $ACTUATOR_PORT:8081 --name $BUILD_NAME $BUILD_NAME
# docker stop $BUILD_NAME
