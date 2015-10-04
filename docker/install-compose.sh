#!/bin/bash

# Create admin in wildfly
docker build -t wildfly-management .

# Run containeres 
docker-compose up -d dcdbfri_wildfly

# Package and deploy dcdbfri on the Wildfly server
cd ../
mvn clean package wildfly:deploy
cd -
