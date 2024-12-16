#FROM is used to tell docker that it has dependency with other image so download that image and start with that as base image
FROM openjdk:17-jdk-slim

MAINTAINER reachsheyam.com

#we are instructing docker to copy the jar from the path into docker image(jdk+accounts jar)
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Command to run the jar similar to whjat we do using java comd
#java -jar .\target\accounts-0.0.1-SNAPSHOT.jar (but here we have already coped the jar into this location)
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]