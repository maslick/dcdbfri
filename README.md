# dcdbfri
JavaEE + WildFly + JAX-RS + JPA (from eclipselink) + MariaDB/MySQL, HTML5/AngularJS




## Installation
* Clone and package
```
git clone https://github.com/maslick/dcdbfri
cd dcdbfri
mvn package 
```
* Deploy the `war` package onto the server (e.g. Wildfly): `mvn wildfly:deploy`
* Open `localhost:8080/wildfly-helloworld/` in your browser

