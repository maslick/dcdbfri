# =dcdbFRI=
JavaEE + WildFly + JAX-RS + JPA (from eclipselink) + MariaDB/MySQL, HTML5/AngularJS




## Installation
* Clone and package
```
$ git clone https://github.com/maslick/dcdbfri
$ cd dcdbfri
$ mvn package 
```
* Configure Mysql
```
$ mysql.server start
  SUCCESS! MySQL running (8008)
```

```
$ mysql -u root -p
mysql> create database dcdbfri;
mysql> create user bled@'%' identified by 'bled';
mysql> create user bled@'localhost' identified by 'bled';
mysql> grant all on *.* to bled@'%';
mysql> grant all on *.* to bled@'localhost';
mysql> flush privileges;
$ mysql.server restart
```
* Deploy the `war` package onto the server (e.g. Wildfly): `mvn wildfly:deploy`
* Open `localhost:8080/wildfly-helloworld/` in your browser


## Using docker
* Clone and package
```
$ git clone https://github.com/maslick/dcdbfri
$ cd dcdbfri
$ mvn package 
```
* Start MariaDB in a docker container:
```
$ docker run --name=some-mariadb -e MYSQL_ROOT_PASSWORD=root12 -e MYSQL_DATABASE=dcdbfri -e MYSQL_USER=bled -e MYSQL_PASSWORD=bled -d mariadb
```

* Build wildfly-management container
Create a file named Dockerfile:
```
FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent               
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
```

Build the container `$ docker build -t wildfly-management .`
Now you can access the WildFly management console with the following credentials `admin/Admin#70365`.  

* Start WildFly in a container:
```
$ docker run --link some-mariadb:mysql -d -p 8080:8080 -p 9990:9990 wildfly-management
```
* Open a browser and navigate to the manager console (`$IP_ADDR:9990`). Click Deployments->Add and upload the war file we built previously.
* Alternatively, clone the repo on the docker host system and deploy by issuing `mvn package wildfly:deploy`. You will be prompted for username/password (use `admin/Admin#70365`).

* Go to `$IP_ADDR:8080/wildfly-helloworld/` in your browser.
