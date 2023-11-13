# Price Tester
Project to demonstrate API testing

## Table of Contents
* [Getting Started](#getting-started)
* [Running Tests](#running-tests)
* [Technologies](#technologies)
* [Acknowledgments](#acknowledgments)
  * [Changes](#changes)
  * [Technology Choices](#technology-choices)
    * [JPA](#jpa)
    * [Hibernate](#hibernate)
    * [Microservice](#microservice)
    * [Layering](#layering)
    * [Exceptions](#exceptions)

## Getting Started
1. Clone the repository
2. Open the project in your IDE
3. Download maven dependencies
4. Check application.properties for limits setup _(optional)_
5. Run the application with `mvn spring-boot:run`
6. Go to localhost:8080/prices/brands/{brandId}/products/{productId}?date={date}

## Running Tests

1. Execute all points of [Getting Started](#getting-started) at least up to point 4.
2. execute `mvn integration-test` in the terminal

or

1. Execute all points of [Getting Started](#getting-started) at least up to point 4.
2. Execute the test class `PriceControllerIntegrationTests` in the test folder
3. Execute the test class `PriceControllerUnitTests` in the test folder

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

### Changes
1. The PRICE_LIST field has been changed to ID for a better understanding of the API.
2. The PRICE field has been changed to PRICE_VALUE for a better understanding of the API and avoid confusion with the table/class naming.
3. All the fields have been set not nullable since alla the fields seems to be mandatory.
4. For the fields priority, curr and price I have set a default value in order to avoid null values. Respectively:
   * 0 for priority 
   * 0.00 for price 
   * EUR for curr
5. The values provided for the dates have been changed accordingly to match the default pattern for database insertion. 
6. For the field price the type of value used is BigDecimal to avoid problems caused by the Double class.
7. Some data has been added to the data.sql file to cover other test cases not covered by the default data provided.

### Technology choices

#### JPA
The PriceRepository Interface has been created with the
extension of the JpaRepository interface.
```java
@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
   ...
}
```
This has been done due to the maintainability and clarity
that the JPA standard provides. By following the standard,
the code is more readable and there is plenty of documentation
on how to create them and what each bit of the methods 
created translates to. I preferred to follow this direction
as the Spring Boot framework really benefits from the standards
and should guarantee that even in future updates the code
doesn't need any modifications.
Creating query manually could be more efficient, as there
would not be any query building process in the way, but it
also leads to human prone errors. It could also require more
testing as the developer writing the query has no real way 
to be sure that the query is semantically correct if not by 
running the query on the database or using secondary tools.
There are plenty of pros and cons between the two approach,
I personally went with the more maintainable option.

#### Hibernate
The Entity Price it's the object we are using to collect
data from the H2 database.
It is defined using Hibernate annotations as shown here:
```java
@Entity(name = "PRICE")
public class Price {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column(nullable = false)
   private Long brandId;
   ...
   @Column(nullable = false, columnDefinition = "int default 0")
   private Integer priority;
   ...
}
```
As Hibernate is very reliable and widely documented and
used ORM framework, I used it as well to create the database
entities automatically upon the application execution.
By configuring the class properly, we centralize the entity/table
definition on the java class which ensures that everything
that is set on the class is reflected in the database as well.
This makes the application more maintainable by not having
to adapt any modification from the Class/Database.

#### Microservice
The microservice architecture has been chosen as I don't have
enough knowledge on the hexagonal pattern.
I think the microservice fits well in this project as it can be 
containerized easily as it is really independent having the
H2 database embedded making it modular.

#### Layering
The project has been divided in 3 layers:
* Controller
* Service
* Repository

The controller layer is the one that exposes the API to
the outside world. Its main purpose is to receive the
request and send it to the corresponding service layer.
```java
@RestController
public class PriceController {
    private final PriceService priceService;
    ...
    @GetMapping("/prices/brands/{brandId}/products/{productId}")
    public Price getPriorityPriceOfBrandAndProductBetweenDate(@PathVariable(name = "brandId") Long brandId,
                                                              @PathVariable(name = "productId") Long productId,
                                                              @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        ...
        Price price = priceService.findFirstByBrandIdAndProductIdAndDateBetweenStartAndEndDate(brandId, productId, date);
        ...
    }
}
```

The service layer is the one that contains the business
logic. its main purpose is to receive the request from
the controller layer and send it to the corresponding
repository layer. With the data received from the repository
layer, it will elaborate the data and send it back to the
controller layer.
```java
@Service
public class PriceService {
    private final PriceRepository priceRepository;
    ...
    public Price findFirstByBrandIdAndProductIdAndDateBetweenStartAndEndDate(Long brandId, Long productId, LocalDateTime date) {
        return priceRepository.findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(brandId, productId, date, date);
    }
}
```

The repository layer is the one that contains the database
logic. Its main purpose is to receive the request from
the service layer and send it to the database. With the
data received from the database, it will elaborate the data
and send it back to the service layer.
Here I used the JpaRepositoy interface to create the
repository. By using this interface we can create 
methods with a specific naming convention that will
automatically create the query for us.
```java
@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
    ...
    Price findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
```

#### Exceptions
For exception handling I created the class 
PriceResponseEntityExceptionHandler which extends
the class ResponseEntityExceptionHandler and uses the
@RestControllerAdvice annotation to handle the exceptions
thrown by the application.
There is some generic handling and some specific handling.
Everything was done to standardize the responses of the 
APIs to a JSON object that could be accepted and analyzed
from the consumer.
More exception handling could be added where needed.
```java

@RestControllerAdvice
public class PriceResponseExceptionHandler extends ResponseEntityExceptionHandler {

    //some generic handling
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleUnmanagedExceptions
    ...
    
    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgumentExceptions
    ...

    //some specific handling
    @ExceptionHandler(value = { NoPriceFoundException.class })
    protected ResponseEntity<Object> handleNoPriceFoundExceptions
    ...
}

```
The NoPriceFoundException was created to manage the case
where no price is found for the given parameters.

The ErrorResponseObject was created instead as the reference
object to use in case of an error, containing all the
fields necessary for a standard JSON error response.
