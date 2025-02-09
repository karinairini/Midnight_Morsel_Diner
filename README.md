# Midnight Morsel Diner

## Description
The **Midnight Morsel Diner** project is a restaurant management application that consists of **backend**, **frontend**, and an **email service**. The app allows for managing orders, menus, and interactions between restaurant staff and customers. The application uses **JWT (JSON Web Tokens)** for authentication and authorization, with different user roles controlling access to various resources. Unit tests have been implemented for both **backend** and **frontend**.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: Angular
- **Email Service**: JavaMailSender Interface
- **Authentication & Authorization**: JSON Web Tokens (JWT)

## Backend Overview

**Backend** is built using **Spring Boot** and is responsible for handling the core business logic of the application. It features:

- **JWT Authentication**: Secure authentication is implemented using JWT, allowing users to log in and obtain a token that they will use for subsequent requests.
- **Role-Based Authorization**: Different roles are defined, such as **Admin**, **Employee**, and **Client**, each with specific permissions and access to resources.
- **RESTful API**: The backend exposes RESTful endpoints to allow communication with the frontend, performing actions such as creating orders, retrieving the menu, etc.
- **Database**: The backend uses a relational database (PostgreSQL) to store information about users, orders, menu items, etc.
- **Security**: All API endpoints are secured with role-based access control and JWT-based authentication.

## Frontend Overview

**Frontend** is developed using **Angular** and communicates with the backend API to allow users to interact with the application. Features include:

- **User Authentication**: The frontend integrates with the backend for login and token management.
- **Dynamic UI**: The application dynamically loads content such as menu items, orders, and user-specific data.
- **Role-based Views**: Based on the user role, different views and functionalities are displayed (e.g., Admins can manage the employees and reservations, Employees can manage clients, Clients can place orders).
