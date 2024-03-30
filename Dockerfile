FROM gradle:jdk17 AS builder
WORKDIR /AuthTemplate
COPY ./ ./
RUN whereis java
RUN gradle build -x test
FROM openjdk:17
COPY --from=builder /AuthTemplate/build/libs/AuthTemplate-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]