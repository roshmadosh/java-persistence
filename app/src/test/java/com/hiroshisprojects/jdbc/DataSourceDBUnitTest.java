package com.hiroshisprojects.jdbc;


import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
 
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

	@Override
	protected DataSource getDataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://localhost:3306/jdbc_db;init=runscript from 'classpath:schema.sql'");
		ds.setUser("jdbc_user");
		ds.setPassword("password");

		return ds;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
          .getResourceAsStream("data.xml"));
	}

}
