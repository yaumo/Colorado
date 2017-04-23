FROM maven:3.5.0-jdk-8-alpine
MAINTAINER Lars Langhammer

# install java
#RUN apt-get update
#RUN apt-get install -y maven

# add entrypoint
COPY entrypoint.sh /opt/entrypoint.sh
RUN chmod u+x /opt/entrypoint.sh

# add backend
COPY Denver/ /opt/Denver

WORKDIR /opt/Denver
#COPY denver-0.0.1-SNAPSHOT.war /

#Expose Port for communcation
EXPOSE 8080

ENTRYPOINT ["/opt/entrypoint.sh"]