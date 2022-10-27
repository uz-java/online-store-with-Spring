<h1 align="center">
	<img src="https://readme-typing-svg.demolab.com/?lines=Online+store+website!">
</h1>

# Online store made in Java Spring Boot and Thymeleaf.

------------

#### Use Case - Complete Shopping from Scratch:
- Firstly, must be logged in to the website by using the Login/Register button on the navigation bar.
- To explore the products, the Products button must be clicked. And, the required products are added to Cart.
- Then, when clicked to the Cart button, all the shopping items, their prices and total price appear on the page. Changes can be done in this stage by increasing the quantity of the products or deleting them.
- To proceed to the payment stage, Complete the Shopping button must be clicked.
- Following the Cart confirmation, a new form appears. Information of Address, Credit Card Number is typed.
- The code that has been sent to the registered phone number is typed. (No code being sent to the user, it's just the template for now)
- Finally, Order Summary is created and welcomed to the Customer. If the details of the Order is required, the Order Details button must be clicked.


### Datasource config
```
spring.datasource.url=jdbc:postgresql://localhost:5432/ProjectName
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driver=org.postgresql.Driver
        
```
<br>

### Hibernate config
```
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.hbm2ddl.auto=update
hibernate.cache.use_second_level_cache=false
hibernate.cache.use_query_cache=false
hibernate.maximum.pool.size=20
hibernate.idle.count=10
hibernate.idle.timeout=600000

```

