# Java Persistence in Every Way

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


