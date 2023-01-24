# Java Persistence Several Ways 

### JDBC, without Connection Pool
Serving a DB endpoint with a singleton JDBC connection, just for kicks.  

### JDBC, with Hikari Connection Pool
When `HikariDataSource` configuration is implemented in a `static {}` block as some resources suggest, I ran into issues where my custom data source class 
was not being instantiated in time. This was fixed when I added the logic in a `private static` method.  

In addition, while the Hikari documentation suggests I won't usually have use `setDriverClassName`, this was required in my case. The driver 
class name `org.mysql.cj.jdbc.Driver` was not obvious and required looking up.  

While the connections are assumed to be pooled, the data source from which `.getConnection()` is called is itself a singleton. This was 
implemented by hand.  

For some reason the `datasource.properties` file's `jdbcUrl` attribute could not be directly set to `HikariConfig`. `setDriverClassName` was 
also done from outside the properties file. This meant I had to set configuration properties in two different places, which is not ideal.  

The connection pool lifespan is tied to the Tomcat web server, not to the application. That means if you keep restarting the app without 
restarting the server as well, there will be multiple connection pools open. Unhandled and it will eventually lead to an exception where 
not enough connections are available.  

### JDBC using Spring's JdbcTemplate interface
`JdbcTemplate` abstracts away having to close connections and handle exceptions, and allows for more declarative code.

`JdbcTemplate` needs a `DataSource` implementation to be passed as a constructor argument. The one used was `MysqlDataSource` which 
came with the dependency containing the MySQL driver.

### Testing with DBUnit
DBUnit connects to a live DB and makes actual changes to the DB it connects to. It allows you to define the initial state of the DB using an XML file. Still not sure if it's meant to be used for testing your DAOs, maybe include a flag in all DAOs that determines if its methods act on the "prod" database versus your test database.  

### Hibernate and Spring MVC
The config class needs `@EnableTransactionManagement` if you want to use the transaction manager which creates and commits transactions for 
you.   

- The `DataSource` bean sets the DB connection properties like the connection string, username, and password. 
- The `LocalSessionFactoryBean` bean takes the data source bean as input, and is where you set the location of your hibernate config file 
as well the POJOs hibernate will deal with.
- The `HibernateTransationManager` requires the sessionFactory created early. Spring manages this bean in the background, i.e. we never work directly with it in our DAO or service classes.  

The `HibernateTransationManager`, along with the `LocalSessionFactoryBean` are classes from the `org.springframework.orm.hibernate5` library. These classes make it possible to use a transaction manager and avoid having to open and commit transactions yourself.  

To make Hibernate create tables for your entites, you need to set the property `hibernate.hbm2ddl.auto` to something that isn't `none`. In Spring Boot this will be done for us.  

### H2 Embedded DB Setup
To replicate `spring.h2.console.enabled=true` from Spring Boot (remember that everything we're doing is _without_ Boot), you have to include a bean in your application context from `org.h2.tools.Server` that returns `Server.createWebServer("-web")` and set `start` to be the init method for the bean.  

Then `localhost:8082` is the default path for the H2 console. The name of the server is generated at application start, so you have to check the logs to find the name (e.g. something like `jdbc:h2:mem:e341c501-a499-47dc-9af3-46c35b343d5d`).
