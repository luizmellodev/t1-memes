FROM adoptopenjdk/maven-openjdk11:latest AS compile

COPY . .

RUN mvn clean package -Dmaven.test.skip=true -q

FROM adoptopenjdk/openjdk11:latest

EXPOSE 8080

COPY --from=compile target/orientacao-bucal-backend-0.0.1-SNAPSHOT.jar server.jar

ENTRYPOINT ["java","-jar","server.jar"]