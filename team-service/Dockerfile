FROM java:8-jdk-alpine

COPY ./target/team-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch team-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","team-service-0.0.1-SNAPSHOT.jar"]