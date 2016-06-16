# egen-by-challenge
Restful Webservice using Java Spark Framework

Language: Java

Framework: Java Spark

Database: MongoDb

This application exposes 5 services:  

getAllUsers - http://localhost:8002/api/users, method-GET

createUser - http://localhost:8002/api/users, method-POST

findUser - http://localhost:8002/api/users/:id, method-GET

updateUser - http://localhost:8002/api/users/:id, method-PUT

removeUser - http://localhost:8002/api/users/:id, method-DELETE

Jackson API is used to convert the Object to Json.
The Json data is being stored in the MongoDB database - “userDb”.

Implemented JUnit test cases for all the 5 service methods.
