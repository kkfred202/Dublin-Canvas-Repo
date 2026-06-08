# Dublin Canvas Tracker

A Spring Boot web application for managing public street art records across Dublin, built for the Commercial Solutions Design module at CCT College Dublin.

## Overview

The Dublin Canvas Tracker lets authenticated users manage records of public murals and street art across Dublin City. The dataset contains 898 artworks sourced from data.gov.ie, published by Dublin City Council as part of the Dublin Canvas initiative.

## Features

- User signup and login with BCrypt password hashing
- Full CRUD on artwork records through the browser
- Keyword search by artist name or area
- Server-side form validation using Jakarta Bean Validation
- Automatic CSV data import on first startup
- Street art gallery page
- Responsive UI with custom CSS and Font Awesome icons

## Tech Stack

- Java 17, Spring Boot 3.2.5
- Spring MVC, Spring Security, Spring Data JPA
- Thymeleaf, MySQL 8, Maven

## Setup

You will need Java 17, MySQL 8 and Maven installed before running this.

1. Clone the repo
2. Open MySQL Workbench and run: CREATE DATABASE IF NOT EXISTS dublin_canvas_db;
3. Make sure application.properties has username: root and password: root
4. Place the CSV file inside src/main/resources/
5. Right click DublinCanvasTrackerApplication.java in Eclipse and run as Java Application

Go to http://localhost:8080 in your browser. The dataset loads automatically on the first run.

## Architecture

Three-tier MVC — Browser talks to Spring Security, which routes to Controllers, then Services, then Repositories, then MySQL. Two tables in the database: users and artworks.

## Author

Fredrick Kimutai — Student Number 2024658
BSc in Computing in IT, CCT College Dublin
Dataset sourced from data.gov.ie — Dublin Canvas, Dublin City Council