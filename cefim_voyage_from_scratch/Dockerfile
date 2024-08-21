FROM maven:3.8-openjdk-17 as builder

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn clean install -DskipTests

FROM openjdk:24-slim-bullseye

COPY --from=builder /usr/src/app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]
