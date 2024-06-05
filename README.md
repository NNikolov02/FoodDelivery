# Nikola Nikolov
# FakNum - 201221012

# Food Delivery System

## Introduction
The Food Delivery System is a Java-based web application built with Spring Boot that provides an easy and convenient way for users to order food from restaurants and have it delivered to their doorstep. The system consists of several modules including customer management, restaurant management, menu management, cart management, and delivery guy management.

## Features
Customer Management: Customers can register, login, view their profile, and manage their orders through the system.

Restaurant Management: Restaurants can register, create menus, manage menu items, and assign delivery guys for deliveries.

Menu Management: Restaurants can add, update, and delete menu items such as pizzas, pastas, steaks, and salads.

Cart Management: Customers can add items to their cart, view the contents of their cart, and complete their purchase.

Delivery Guy Management: Delivery guys can view available orders for delivery, update their availability, and mark orders as delivered.

## Technologies Used
The project utilizes the following technologies:

Java: The primary programming language used for development.

Spring Boot: A powerful framework for building Java-based applications.

Spring MVC: Provides a model-view-controller architecture for building web applications.

Spring Data JPA: Simplifies the implementation of data access layers.

Hibernate: An ORM (Object-Relational Mapping) framework for mapping Java objects to database tables.

MySQL: A relational database management system used for data storage.

Maven: A build automation tool for managing dependencies and building projects.

## Project Structure
The project is organized into several packages:

Controllers: Contains classes responsible for handling HTTP requests and responses.

Services: Implements business logic and interacts with repositories to perform CRUD operations.

Mappers: Converts DTOs (Data Transfer Objects) to model objects and vice versa.

Repositories: Defines interfaces for database operations.

## Endpoints
The application exposes several endpoints to perform various operations:

/delivery/cart: Manages cart-related operations such as adding items, viewing cart contents, and completing purchases.

/delivery/customer: Handles customer-related operations including registration, profile management, and cart management.

/delivery/deliveryGuy: Manages delivery guy-related functionalities like viewing orders for delivery and updating availability.

/delivery/menu: Handles menu-related operations such as viewing menus, adding items to menus, and deleting items from menus.

/delivery/restaurants: Manages restaurant-related functionalities including CRUD operations for restaurants and assigning delivery guys to restaurants.

## How to Use
Clone the repository.
Set up the MySQL database and configure the database connection in the application.properties file.
Run the application using Maven: mvn spring-boot:run.
Use Postman or any other API testing tool to interact with the provided endpoints.

## Contributing
Contributions to the project are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.