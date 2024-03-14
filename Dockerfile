FROM openjdk:17-jdk

WORKDIR /app

COPY target/SS-0.0.1-SNAPSHOT.jar /app/SS-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "SS-0.0.1-SNAPSHOT..jar"]

ENTRYPOINT ["java", "-jar", "/SS-0.0.1-SNAPSHOT.jar"]