#!/bin/sh
mvn clean package && docker build -t lessons/beta .
docker rm -f beta || true && docker run -d -p 8080:8080 -p 4848:4848 --name beta lessons/beta 
