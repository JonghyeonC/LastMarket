FROM openjdk:11-jre-slim

COPY build/libs/last-market-0.0.1-SNAPSHOT.jar app.jar

CMD ["java","-jar","-Dspring.profiles.active=server","app.jar"]