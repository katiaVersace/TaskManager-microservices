FROM java:8-jdk-alpine

COPY ./target/task-manager-db-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch task-manager-db-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","task-manager-db-0.0.1-SNAPSHOT.jar"]