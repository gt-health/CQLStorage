# CQLStorage
A CQL Storage service using spring-rest and JPA for database connection.

This is a simple JPA database connection through a rest endpoint.

## Access

You can reach the CQL stored in the database at ```${HOSTNAME}/CQL?name={name}``` where ```HOSTNAME``` is the
deployment location of the app, and ```name``` is the name of the CQL you want to pull.

## Configuration

Look in the ```src/main/resources/application.properties``` for the expected environment variables to be set.
```JDBC_URL``` a jdbc url connection to a provided database
```JDBC_USERNAME``` Username to connect to the db
```JDBC_PASSWORD``` Password to connect to the db

## CQL File Deployment

Every .CQL file deployed in the ```src/main/resources``` folder will be loaded on bootup into the CQLStorage server.
You can access those files through the same name via the service.
