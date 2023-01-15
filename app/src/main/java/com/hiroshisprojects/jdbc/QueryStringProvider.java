package com.hiroshisprojects.jdbc;


abstract class QueryStringProvider<T extends MyModel> {
	protected String tableName;
	public QueryStringProvider(String tableName) {
		this.tableName = tableName;
	}

	public abstract String createTable();

	public abstract String insertInto(T item); 

	public abstract String selectAll();
}
