FROM openjdk:11
MAINTAINER Baglan Zhaparov (stranges661@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn package
COPY ["target/test-1.0-*.jar", "start.sh", "/usr/local/service/"]
CMD ["sh", "start.sh"]

