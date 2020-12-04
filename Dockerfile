FROM openjdk

VOLUME /tmp

EXPOSE 8080

ADD /build/libs/desafio-0.0.1-SNAPSHOT.jar desafio-0.0.1-SNAPSHOT

ENTRYPOINT ["java","-jar","desafio-0.0.1-SNAPSHOT.jar"]