FROM openjdk

VOLUME /tmp

EXPOSE 8080

WORKDIR /app

COPY /build/libs/desafio-0.0.1-SNAPSHOT.jar desafio.jar

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "desafio.jar"]