#!/bin/sh
mvn clean package && docker build -t de.ltux/jsf .
docker rm -f jsf || true && docker run -d -p 8080:8080 -p 4848:4848 --name jsf de.ltux/jsf