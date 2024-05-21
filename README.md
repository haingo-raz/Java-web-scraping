## Overview

This is a web application project that utilizes Java and SQL for web scraping and loading the obtained data onto a website developed using HTML, CSS, and JavaScript.

## Technologies Used

- **Java**: Used for web scraping operations.
- **JUnit**: Used for unit testing.
- **JavaDoc**: Automatically generates API documentation from source code.
- **Threads**: Used to execute code in parallel for web scraping.
- **Maven**: Java Build System used to build, test, and document Java-based projects based on an XML build file. It is used to manage dependencies, download libraries specified in the pom.xml file, and run unit tests with `mvn test`. It can also be used to package the class files as a library with `mvn package`.
- **Spring**: Connects the classes together in the application (beans) and manages dependencies between classes. The relationship between the beans is specified through Java annotations (or an XML file) in the AppConfig file. Spring wires up the beans at runtime. It is used together with Maven by adding Spring dependencies in the pom.xml file.
- **Hibernate**: Object-Relational Mapping framework used to map between Java classes and database tables. It maps Java data types to SQL data types and aims to eliminate 95% of SQL data processing in a program. It is used with Maven, which is configured to download Hibernate libraries and other dependencies (SQL driver for Java). Mapping between Java objects and database tables can be configured using XML files or Java annotations. A database configuration file called `hibernate.cfg.xml` is used to set up the database. A class containing variables that correspond to data in the database needs to be created, and XML files are used to specify the relationship between columns and variables. These files need to be specified in the hibernate.cfg.xml file.
- **JSoup**: A library designed for web scraping. It downloads the HTML and uses CSS selectors to extract data from a website. The JSoup dependency needs to be added in the pom.xml file.
- **SQL**: Used for managing and manipulating scraped data. The data obtained through web scraping is stored in an SQL database, allowing for efficient storage, retrieval, and manipulation of the data. SQL queries are used to perform operations such as searching for specific laptop specifications, comparing prices, and retrieving relevant information for display on the website's front-end.
- **HTML, CSS, and JavaScript**: Used for developing the website's front-end. HTML is used to structure the content of the web pages, CSS is used for styling and layout, and JavaScript is used for adding interactivity and dynamic functionality to the website. In this project, HTML, CSS, and JavaScript are utilized to create an intuitive and visually appealing user interface for displaying the scraped data, search results, and laptop comparisons.
- **Node.js and Express**: Used to handle incoming requests from the front-end, process search queries, retrieve data from the SQL database, and send the results back to the front-end. They provide the backend functionality necessary for executing search operations, retrieving laptop data, and handling user interactions.

## How does the project work?

### Populating the database

The database in this project is populated by utilizing web scraping techniques. The Java code uses the JSoup library to download the HTML of a website and extract the desired data using CSS selectors. The scraped data is then processed and stored in the SQL database. This process is executed in parallel using threads to improve efficiency. The web scraping operation is triggered by running the Java code, which initiates the scraping process and populates the database with the obtained data.

### Searching for a laptop

To search for a laptop in the search bar of this project, the user can enter the desired laptop specifications or keywords related to the laptop they are looking for. The entered search query is then passed to the backend of the web application, where it is processed and used to perform a search operation on the SQL database. The backend code involves constructing an SQL query that includes the search query as a parameter and executing it against the database. The search results are then retrieved from the database and displayed to the user on the website's front-end. This allows the user to easily find laptops that match their search criteria and view the relevant information and details.

### Comparing laptop prices

The backend code involves querying the database for laptops with the same name and brand and retrieving their corresponding prices. The prices are then displayed on the website's front-end, allowing users to easily compare the prices of laptops with the same name and brand. This feature enables users to make informed decisions when purchasing laptops, as they can quickly identify any price variations among laptops of the same name and brand.

## Screenshots

### Search and results

![Search results](https://github.com/haingo-raz/Java-web-scraping/blob/master/website/app/public/images/search_results.png)

### Product comparison

![Laptop comparison](https://github.com/haingo-raz/Java-web-scraping/blob/master/website/app/public/images/price_comparison.png)


## Setup

### Prerequisites

To set up and run this project on your computer, you will need the following prerequisites:

- Java development environment: Install the Java Development Kit (JDK) on your computer.
- Node.js and npm installed: Install Node.js and npm (Node Package Manager) on your computer.
- SQL database: Set up an SQL database on your computer.

### How to run the project on your computer

Once you have the prerequisites installed, follow these steps to run the project on your computer:

1. Clone the project repository: Open a terminal or command prompt and navigate to the directory where you want to clone the project. Then, run the following command:
    ```
    git clone https://github.com/haingo-raz/Java-web-scraping.git
    ```

2. Set up the Java environment.

3. Set up the SQL database: Create a new database in your SQL database management system. Update the database configuration in the project's configuration files (e.g., `hibernate.cfg.xml`) to connect to your database.

4. Install XAMPP: Download and install XAMPP, which includes Apache, MySQL, and PHP. XAMPP provides an easy way to set up a local web server environment for development purposes.

5. Start XAMPP: Open XAMPP and start the Apache and MySQL services.

6. Set up HeidiSQL: Download and install HeidiSQL, a graphical interface for managing MySQL databases. Use HeidiSQL to create the necessary databases and tables for your project.

7. Install Maven: If you don't have Maven installed, you will need to install it.

8. Install project dependencies: Open a terminal or command prompt, navigate to the project directory, and run the following command:
    ```
    mvn install
    ```

9. Run the Java web scraping code: In your Java IDE, run the main Java class that triggers the web scraping process. This will download the HTML, extract the desired data, and populate the SQL database.

10. Set up the Node.js backend: Open a terminal or command prompt, navigate to the `website` directory in the project, and run the following command to install the required Node.js dependencies:
    ```
    npm install
    ```

11. Start the Node.js backend server: Run the following command to start the Node.js backend server:
    ```
    npm start
    ```

12. Access the website: Open a web browser and navigate to `http://localhost:5000` to access the website. You should be able to search for laptops, view search results, and compare laptop prices.