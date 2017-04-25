#!/bin/bash
# start Server
mvn test
java -jar /denver-0.0.1-SNAPSHOT.jar
#mvn package spring-boot:run 

#endless loop
/usr/bin/tail -f /dev/null