# GILDED ROSE Task
Repository to store used code to do the task as part of UpWork selection

## About
```
- This project is did create to simulate the buy of items
- There are three main endpoints:
    - GET all valid items (inventory)
    - GET specific item by id (to visualize that item)
    - POST Buy a item by id
- Only the buy endpoint requires to be authenticated
- Every 10 views in a hour os same item, the price this item is increased in 10%
- To simulate this situation, is necessary call the "item by id" endpoint to the 
  same item more than 9 times in a hour
```

## Business Role
```
- To attend the business role, was create a logical that store the all visualizations of an item. 
  Every call to get item by id through endpoint, is checked if that item has ten or more views in
  the last hour.
- When it happens, the price of this item is increased in 10 percent.
- After this, all visualizations this item is removed and the count is reseted.
```

## Security
```
- I did use a simple authentication with Spring Boot Security because I wanted a 
  simple way to authenticate the user.
- The username and password are fixed inside the code.
- username: admin
- password: gildedrose
```

## Database
```
- Was used the H2 Database, and has a file inside the resources folder named "data.sql" 
  where are the scripts to create and insert values.
- This script is executed when the application goes up.
```

## Architecture
```
- Java 11
- Spring Boot
- Maven (dependency management)
- Unit Test with Junit/Mockito
- H2 Memory Database
- Spring Security to make the Authorization (basic auth)
```

## Estructure
```
- To this project was create a structure that I have been used in my last projects seemed like this.
- This structure respect that main layers (controller, service and repository) and the business rule
  are concentrated in the service, decoupled by an interface.
- The spring anotations was used in these layers.
- A simple response object was also created to return the items.
- The repositories were mapped using Spring Data JPA.
```

## Instructions to run the project


#### Prerequisite
```
Java 11
```

##### To do the clone or download of project
```
After downloading the project, access the root folder and to execute the command to maven run.
- mvn clean install
```

#### Run the project with Spring Boot Embedded Server (by default, will use 8080 port)
```
mvn spring-boot:run
```

#### Examples of datas
```
GET /v1/items
HttpStatus: 200
[
    {
        "id": 1,
        "name": "Item 01",
        "description": "Item 01",
        "price": 100
    },
    {
        "id": 2,
        "name": "Item 02",
        "description": "Item 02",
        "price": 250
    }
    
]
```

```
GET /v1/items/{itemId}
HttpStatus: 200
    {
        "id": 1,
        "name": "Item 01",
        "description": "Item 01",
        "price": 100
    }
```

```
POST /v1/items/{itemId}/buy 
header ==> 'Authorization: Basic YWRtaW46Z2lsZGVkcm9zZQ=='
HttpStatus: 200
    {
        "id": 1,
        "name": "Item 01",
        "description": "Item 01",
        "price": 100
    }
```

Obs: The file with collection of postman it be in the root folder of project "GildedRose.postman_collection.json"