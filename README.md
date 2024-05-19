## Overview

Web application project utilizing Java and SQL for web scraping and loading the obtained data onto a website developed using HTML, CSS, and JavaScript.

## Technologies Used

- **Java**: Utilized for web scraping operations.
- **JUnit**: Unit testing.
- **JavaDoc**: Automatically generate API documentation from source code.
- **Threads**: Used to execute codes in parallell for the web scrapping.
- **Maven**: Java Build System used to build, test, and document Java-based projects based on XML build file. Used to manage the dependencies. Downloads libraries specified in the pom.xml file. The pom file defines plugins used during the build process, files to build/package, libraries and versions to build and package with, and how to package the project. Used to run unit tests with `mvn test`. Can be used to package the class files as a library with `mvn package`.
- **Spring**: Connects the classes together in the application (beans). Manage dependencies between classes. Specify the relationship between the beans through Java annotations (or an XML file) in the AppConfig file. Spring wires up the beans in the runtime. Used together with Maven by adding Spring dependencies in the pom.xml file.
- **Hibernate**: Object-Relation Mapping framework used to map between Java classes and database tables. Maps Java data types to SQL data types. Aims to eliminate 95% OF SQL data processing in a program. Used with Maven which is configured to download hibernate libraries and other dependencies (SQL driver for Java). Mapping between Java objects and database tables can be configured using XML files or Java annotations. Set up with a database configuration file called `hibernate.cfg.xml`. Class containing variables that correspond to data in database need to be created and XML files are used to specify the relationship between columns and variables. These files need to be specified in the hibernate.cfg.xml file.
- **SQL**: Employed for managing and manipulating scraped data.
- **HTML, CSS, and JavaScript**: Used for developing the website's front-end.
- **Node.js and Express**: Implemented for server-side operations.

## How does the project work?
### Populating the database


### Searching for a laptop

### Comparing laptop prices


## Screenshots
### Search and results
![Search results](https://github.com/haingo-raz/Java-web-scraping/blob/master/website/app/public/images/search_results.png)

## Product comparison
![Laptop comparison](https://github.com/haingo-raz/Java-web-scraping/blob/master/website/app/public/images/price_comparison.png)

## Setup

### Prerequisites

- Java development environment
- Node.js and npm installed
- SQL database
