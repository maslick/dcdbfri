# dcdbfri
JavaEE + WildFly + JAX-RS + JPA (from eclipselink) + MariaDB/MySQL, HTML5/AngularJS




#Installation
1. Clone and package
```
git clone https://github.com/maslick/dcdbfri
cd dcdbfri
mvn package 
```
2. Deploy the `war` package onto the server (e.g. Wildfly): `mvn wildfly:deploy`
3. Open `localhost:8080/wildfly-helloworld/` in your browser
