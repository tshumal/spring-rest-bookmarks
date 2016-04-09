# My take on the Building REST services with Spring tutorial, adding a AngularJS front end
This is my take on the Building REST services with Spring tutorial from https://spring.io/guides/gs/rest-service/
In my adaptation I have added an **AngularJS** front end that consumes the restful / hateoas endpoints.
I also demonstrate how **Spring Boot**, **Spring Data JPA**, **Spring Security** and in the front-end **AngularJS** can be used together to easily write web app.

## Frameworks

### Front-end

#### Twitter Bootstrap
For rapidly creating prototypes of a web application, a UI toolkit or library will become really handy. There are many choices available, and for this example I chose Twitter Bootstrap.

#### AngularJS
AngularJS is a MVC based framework for web applications, written in JavaScript. It makes it possible to use the Model-View-Controller pattern on the front-end. It also comes with several additional modules. In this example I'm also using **angular-resource**, which is a simple factory-pattern based module for creating REST clients.

### Back-end

#### Spring Boot
One of the hassles while creating web applications using the Spring Framework is that it involves a lot of configuration. Spring Boot makes it possible to write configuration-less web application because it does a lot for you out of the box.
For example, if you add H2 as a dependency to your application, it will automatically provide a datasource to it.
If you add the spring-boot-starter-security dependency,you provide primitives for securing application access and If you add the spring-boot-starter-web dependency, then you can start writing controllers for creating a web application.


#### Spring Data JPA
Spring Data JPA allows you to create repositories for your data without even having to write a lot of code. The only code you need is a simple interface that extends from another interface and then you're done.
With Spring Boot you can even leave the configuration behind for configuring Spring Data JPA, so now it's even easier.

#### Spring Security
By securing both the web layer of your application and the methods behind the scenes, Spring Security makes sure that no logic will be executed in your application unless the user is both authenticated and authorized.


## Installation
Installation is quite easy, first you will have to install some front-end dependencies using Bower:
```
bower install
```

Then you can run Gradle to build the application:
```
gradle clean build
```

Now you can run the Java web application quite easily, this automatically starts Spring Boot's embedded tomcat container:
```
java -jar target\spring-rest-bookmarks-1.0.0.jar
```

