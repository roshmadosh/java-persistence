package com.hiroshisprojects.jdbc.data;

import java.util.List;

import com.hiroshisprojects.jdbc.MyModel;

public abstract class Dao<T extends MyModel> { 
	protected abstract void createTable();
	protected abstract void insertInto(T item);
	protected abstract List<T> selectAll();
	
}

