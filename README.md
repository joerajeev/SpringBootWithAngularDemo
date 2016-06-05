# SpringBootWithAngularDemo
===========================

Demonstrates RESTful web services with spring boot and an Angular web app around it.

Authors
-------
* "Joseph Rajeev Motha" <joerajeev@gmail.com>
 
Features
--------
The application provides the ability to search existing adverts for cars, and add new adverts.
If the new advert is for an existing vehicle in the system (an advert for it was created previously), then the user could simply
search for the vehicle using it's registration number and the application would pre fill all the vehicle details.

Caveats
-------
This application is purely to demonstrate some of the capabilities of  Spring boot and Angular and is not to be treated as an enterprise ready application.
Especially the UI - not much time was spent on styling. You may notice however that the view advert page is made to be responsive - it caters for the users screen size. 

Setup
-------
* Simply run the project with mvn spring-boot:run. Spring Boot will spin up a tomcat instance on 8080. The database is pointing at a AWS S3 instance that's already pre populated with some data.
* If a local datbase is required for development purposes it can be created using the scripts provided in resources/create_db.sql. It was created with MySQL workbench and may need tweaking to work with other DB's. You should then update the application-dev.properties with the credentials of your new DB and tell spring boot to use the dev profile by updating spring.profiles.active=dev in the application.properties file.

