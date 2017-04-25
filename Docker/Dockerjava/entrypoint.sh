#!/bin/bash
# start Server
#java -jar /denver-0.0.1-SNAPSHOT.war
mvn package spring-boot:run 

#endless loop
/usr/bin/tail -f /dev/null