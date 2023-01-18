package vn.ptit.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


public abstract class IDAO<T> {
	Statement statement;
	PreparedStatement preStatement;
	Connection conn;
	ResultSet rs;

	public abstract List<T> selectAll();
        public abstract T findById(int id);
	public abstract int insert(T object);
	public abstract int update(T object);
        public abstract int delete(int id);
	public abstract void closeConnection();
}
