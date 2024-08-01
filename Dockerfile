FROM maven:3.8.3-openjdk-17
WORKDIR /app
COPY ../.. /app/
RUN mvn clean package -DskipTests
ENTRYPOINT ["mvn", "spring-boot:run"]
CMD ["-Dspring-boot.run.arguments=--spring.config.location=classpath:/application-docker.yml"]

