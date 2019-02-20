FROM maven:3.6.0-jdk-8 AS builder
WORKDIR /usr/src/cql_storage
ADD . .
RUN mvn clean install -DskipTests -f /usr/src/cql_storage

FROM tomcat:alpine
#move the WAR for contesa to the webapps directory
COPY --from=builder /usr/src/cql_storage/target/CQLStorage-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/CQLStorage.war