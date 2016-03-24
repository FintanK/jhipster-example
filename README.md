Jhipster Example
==========================

This is a simple example of a full stack application built using JHipster.
It create entities for authors, categories and posts.

![](http://jeffsheets.github.io/BuzzworthyJava/assets/img/hipster-logo.png)


Components
==========================

- Node.js / NPM
- Angular.js
- JAVA Spring
- Grunt / Bower
- MySQL database


Frameworks / libraries
==========================

- Gatling for performance testing
- Liquidbase for migrations
- Phanton.js and Karma for behavioural testing
- BrowserSynch
- Dropwizard metrics
- Swagger
- Wiredep

Features
==========================

- Internationalization
- Generated deployments for heroku and OpenShift
- EditorConfig


Installation
==========================

Make sure you have JAVA 1.8 installed.
Navigate to the root directory.

> mvn clean install

This should take care of the dependencies for both the front and backend components.


Usage
======

In the root directory.

Build and run the project (this will build and run the frontend and backend).

> mvn spring-boot:run

Run the frontend in isolation (development mode)

> grunt serve


Development
============

Generating entities

> yo jhipster:entity "yourentity"

https://jhipster.github.io/creating_an_entity.html

You will be able to specify the fields, validations and relationships with other entities and have the following generated automatically...

- A database table
- A Liquibase change set
- A JPA Entity
- A Spring Data JPA Repository
- A Spring MVC REST Controller, which has the basic CRUD operations
- An AngularJS router, a controller and a service
- An HTML view

Metrics will also be available via the dashboard.

Credentials
=============

Default username and password: admin / admin

URL
==========================

http://127.0.0.1:8080/api - API
http://localhost:8080/ - UI
http://localhost:3001/ - BrowserSynch
