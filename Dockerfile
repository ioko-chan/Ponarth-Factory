FROM gradle:jdk17 AS builder
WORKDIR /AuthTemplate
COPY ./ ./
RUN gradle build -x test
RUN ls -al
RUN cd build && ls -al
RUN find -name "*.jar"
RUN pwd
FROM openjdk:17
RUN pwd
RUN ls -al
COPY --from=builder /AuthTemplate/build/libs/AuthTemplate-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]