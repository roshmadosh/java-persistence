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


### JDBC using Spring's JdbcTemplate interface
`JdbcTemplate` abstracts away having to close connections and handle exceptions, and allows for more declarative code.

`JdbcTemplate` needs a `DataSource` implementation to be passed as a constructor argument. The one used was `MysqlDataSource` which 
came with the dependency containing the MySQL driver.




