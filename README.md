Welcome to my Java Enterprise Java backend exam project. The idea is the back-end logic of a simple webshop api that I will develop further later.
It is a multi-layered application using Java Spring and H2 database.

To start the app, please run the following batch files:
1. build.bat - creates the jar file on run
2. run.bat - creates the docker image file
   optional - use Swagger to check the endpoints at: http://localhost:8080/swagger-ui/index.html

Entities:
Location (id, locationCode, name), Product (id, name, price), User (id, name, location).

Connection between entities:
Location -- OneToMany -- User
Product -- ManyToOne -- User
User --OneToMany -- Product

