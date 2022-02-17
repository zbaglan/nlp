FROM openjdk:11
MAINTAINER Baglan Zhaparov (stranges661@gmail.com)
RUN apt-get update
RUN apt-get install maven -y
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn clean package
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]