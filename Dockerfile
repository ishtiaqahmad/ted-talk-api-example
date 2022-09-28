FROM openjdk:17-jdk-slim-buster
WORKDIR /app

copy ./target/ted-talk-api-0.0.1-SNAPSHOT.jar /app/ted-talk-api-0.0.1-SNAPSHOT.jar
#CMD ["java","-jar","ted-talk-api-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "/app/ted-talk-api-0.0.1-SNAPSHOT.jar"]