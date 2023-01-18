package com.hiroshisprojects.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.hiroshisprojects.jdbc.employee.Employee;
import com.hiroshisprojects.jdbc.employee.EmployeeDao;
import com.hiroshisprojects.jdbc.employee.JdbcDao;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JdbcTemplateConfig.class, WebConfig.class })
@WebAppConfiguration // this adds a servlet context
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

	private final Logger logger = LoggerFactory.getLogger(DataSourceDBUnitTest.class);;
	@Autowired
	private JdbcDao employeeDao;

	@Override
	protected DataSource getDataSource() {

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
		
		return ds;
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
          .getResourceAsStream("data_init.xml"));
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

	
	@Test
	public void testGivenSingleEmployee_whenDaoSelectsAll_thenSingleEmployeeReturned() throws Exception {

	}
	
}
