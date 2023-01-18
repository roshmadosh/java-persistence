package com.hiroshisprojects.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

	private Logger logger;


	@Override
	protected DataSource getDataSource() {

		logger = LoggerFactory.getLogger(DataSourceDBUnitTest.class);

		
		Properties props = new Properties();
		try (InputStream input = DataSourceDBUnitTest.class.getClassLoader().getResourceAsStream("datasource.properties")) {
			props.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(props.getProperty("dataSource.jdbcUrl"));
		ds.setUser(props.getProperty("dataSource.user"));
		ds.setPassword(props.getProperty("dataSource.password"));
		
		try {
			IDatabaseConnection connection = new DatabaseConnection(ds.getConnection());
			connection.getConfig()
			  .setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
          .getResourceAsStream("data.xml"));
	}

	@Override
	protected DatabaseOperation getSetUpOperation() {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() {
		return DatabaseOperation.DELETE_ALL;
	}

	@Test
	public void testGivenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
		IDataSet expectedDataSet = getDataSet();
		ITable expectedTable = expectedDataSet.getTable("employees"); 

		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("employees");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
}
