<h1 align="center">ParKing</h1>

<h4 align="center">Project build during an internship program.</h4>

<p align="center">
  <a href="https://github.com/isd-soft/parking">
    <img src="https://ibb.co/xznXWkK" alt="Logo" width="80" height="80">
  </a>
  <a href="http://isd-soft.com/"><img src="https://ibb.co/hLVfRg4" title="ISD::Software" alt="ISD::Software"></a>
   </br>
   <a href="https://github.com/isd-soft/parking/issues">Report Bug</a>
   ·
   <a href="https://github.com/isd-soft/parking/issues">Request Feature</a>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Used technologies](#used-technologies)
* [Getting Started](#getting-started)
  * [Get the project](#get-the-project)
  * [Tools](#tools)
  
* [BACKEND](#BACKEND)
  * [Steps to Setup the Server](#steps-to-setup-the-server)
  * [DataBase](#database)
  * [Build backend](#build-backend)
  * [Steps to Setup the Server](#steps-to-setup-the-server)
  
* [FRONTEND](#FRONTEND)
  * [Steps to Setup the Client Web App](#steps-to-setup-the-client-web-app)
  * [Development server](#development-server)
  * [Build frontend](#build-frontend)
  
* [How to use application](#how-to-use-application)
  * [Main page](#main-page)
  * [Statistics page](#statistics-page)
  * [Parking layout page](#parking-layout-page)
  
* [How to use API](#how-to-use-api)
* [Contributing](#contributing)
* [License](#license)


### About the project
This project is made to provide the ISD company parking area with the necessary equipment and software tools control and fixing the employment status of parking lots and maintaining daily statistics on status changes.

The project aims to create a new informative application for monitoring and providing information for end-users about free parking spaces. The purpose of this application is to provide the information in advance if there is an avaliable parking lot or you have to pick up another place.

### Used technologies
Frontend single page application  - Angular Framework,
Backend service                   - Spring Boot Framework + JPA, Hibernate,
Database                          - PostgreSQL database.

Here you will find an implementation of Restful API (Server) & Web Single page application(Client).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment notes from below on how to deploy the project on a live system.

## Get the project 
 Clone the project from git, using git clone https://github.com/isd-soft/parking.git command

## Tools
To get it started could be used IntelliJ IDEA (2019.3.1 Ultimate version).
Also should be installed Lombok plugin for IntelliJ IDEA.

# BACKEND
# Steps to Setup the Server

Deployment can be done on Windows/Linux/Mac OS with these components installed:

* [Java JDK 8 and above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/) (recommended separate system install)
* [PostgreSQL 12](https://www.postgresql.org/download/)

## DataBase
To setup the database you first need to install a PostgreSQL server [link](https://www.postgresql.org/download/). After you've installed it add the bin folder to your PATH system variable.
Backend application provides an automatic database structure creation for first launch. After first start it initiates database with parking lots data depending on the amount of indicated in com.isd.parking.model.ParkingNumber.class

## Build backend

Build and run backend part project:
`mvn clean install`
`mvn spring-boot:run`

Or if use IntelliJ IDEA click on the `Run` button.
 
# FRONTEND
# Steps to Setup the Client Web App

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.3.22.
Deployment can be done on Windows/Linux/Mac OS with these components installed:

* [Java JDK 8 and above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [node-v12.14.1LTS and above](https://nodejs.org/en/)
* [Angular CLI Framework](https://angular.io/cli) - Install the CLI on your machine using the npm package manager: npm install -g @angular/cli

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build frontend

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

Or if use IntelliJ IDEA click on the `Run` button.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).


# How to use application
If you start the application, you will see the main "Dash Board" page with parking lots cards.

## Main page
In this page you can see all parking lots and theirs statuses. By clicking on the card of parking lot, you will be able to see parking lot details.

## Statistics page
Here you can see all statistics records for last week period. You can sort data by date or choose only specified parking lot number.

## Parking layout page
This page represents an interactive parking plan with provided legend. By clicking on the parking lot, you will be able to see parking lot details.

# How to use API

## List of existing controllers:
```
ParkingLots
Statistics

```

### ParkingLots has following requests:

/parking
```
   /**
     * Get parking lots controller request
     * Used to get all parking lots from the local Java memory
     *
     * @return Parking lots list
   */
```

/parking/id
```
   /**
     * Get parking lots by id controller request
     * Used to get parking lot by its id from the local Java memory
     *
     * @return ResponseEntity.OK with body of Parking lot if exists in storage else
     * @throw ResourceNotFoundException
    */
```

### Statistics has following requests:

/statistics
```
   /**
     * Get all statistics records controller request
     * Used to get all statistics records from database
     *
     * @return Statistics records list
   */
```

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](http://opensource.org/licenses/mit-license.php)**
- Copyright 2020 © <a href="http://isd-soft.com/" target="_blank">ISD internship Team</a>.
