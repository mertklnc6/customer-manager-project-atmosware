# Customer Management System Rest API

This repository contains a simple **Customer Management System**. It is built using the **Java** programming language and follows a **layered architecture** in a **monolithic structure**.

## Development

- [Conventional Commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
- Use `@Getter`, `@Setter`, ... instead of `@Data` annotation in classes. It provides more modularity and flexibility.
- Package names should be in lower_snake_case.
- Database table names should be in lower snake case. For example **crm_customers**
- Use type names instead of the `var` keyword. For example **List<Customer> customers** instead of **var customers**
- Use record types instead of classes for immutable data. For example request and response objects.
- Rest api standards in the relevant link must be
  followed. [REST API URI Naming Conventions and Best Practices](https://restfulapi.net/resource-naming/), [Best practices for REST API design](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)
- Finally, review your codes periodically.

## Running the Project
1. Download the Project: Clone or download the project files to your local machine.

2. Run Docker: Ensure Docker is installed and running.

3. Start the Application with Docker Compose: Open a terminal in the project directory and run the command `docker-compose up -d`.

4. PostgreSQL Database and Application: Once the build process is complete, the necessary PostgreSQL database and the application itself will be up and running in the 'customer-management' container.