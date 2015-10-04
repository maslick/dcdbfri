#!/bin/bash

# Run MariaDB
docker run --name=some-mariadb -e MYSQL_ROOT_PASSWORD=root12 -e MYSQL_DATABASE=dcdbfri -e MYSQL_USER=bled -e MYSQL_PASSWORD=bled -d mariadb

# Create admin in wildfly
docker build -t wildfly-management .
docker run --link some-mariadb:mysql -d -p 8080:8080 -p 9990:9990 wildfly-management

# Package and deploy dcdbfri on the wildfly server
cd ../
mvn clean package wildfly:deploy
cd -
