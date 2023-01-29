# Getting Started

### Reference Documentation
A sample application which shows the usage of spring data and spring jpa 3 which is build with spring boot 3 and  

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.1/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#using.devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#web.security)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#howto.data-access.exposing-spring-data-repositories-as-rest)

### Guides
The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)

Use http://127.0.0.1:8080/h2-console to connect to h2 database you can use
```
SELECT * FROM ARTICLE 
```
You can see that the data base is up an running and it has data


You can start h2 console directly

C:\Users\a_fattahi\.m2\repository\com\h2database\h2\2.1.214
 java -cp h2-1.4.194.jar org.h2.tools.Server -tcp -web -browser
 
 
  "D:\Projects\sts-4.17.1.RELEASE\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.5.v20221102-0933\jre\bin\java.exe" -jar lombok.jar delombok D:\Projects\workspace-spring-tool\demo\src\main\java\com\example\spring\jpa\model\ -d d:\test