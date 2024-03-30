FROM gradle:8.7.0-jdk17-alpine AS builder
WORKDIR /AuthTemplate
COPY ./ ./
RUN echo $JAVA_HOME
RUN gradle build -x test
FROM openjdk:17-oracle
COPY --from=builder /AuthTemplate/build/libs/AuthTemplate-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]