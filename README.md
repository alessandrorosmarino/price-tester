# Price Tester
Project to demonstrate API testing

## Table of Contents
* [Getting Started](#getting-started)
* [Technologies](#technologies)
* [Acknowledgments](#acknowledgments)

## Getting Started
1. Clone the repository
2. Open the project in your IDE
3. Run the tests
4. Run the application
5. Use the API

## Technologies
* Java 17
* Spring Boot
* Maven
* JUnit
* Mockito
* H2 Database
* JPA
* Hibernate

## Acknowledgments
1. The PRICE_LIST field has been changed to ID for a better understanding of the API.
2. The PRICE field has been changed to PRICE_VALUE for a better understanding of the API and avoid confusion with the table/class naming.
3. All the fields have been set not nullable since alla the fields seems to be mandatory.
4. For the fields priority, curr and price I have set a default value in order to avoid null values. Respectively:
   * 0 for priority 
   * 0.00 for price 
   * EUR for curr
5. The values provided for the dates have been changed accordingly to match the default pattern for database insertion.
Fot the field price the type of value used is BigDecimal to avoid problems caused by the Double class.
6. Some data has been added to the data.sql file to cover other test cases not covered by the default data provided
