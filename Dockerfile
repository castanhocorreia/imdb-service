FROM openjdk:11-jre-slim
WORKDIR /opt/imdb-service
COPY /target/imdb-service.jar .
SHELL ["/bin/sh", "-c"]
EXPOSE 8080
CMD java -jar imdb-service.jar
