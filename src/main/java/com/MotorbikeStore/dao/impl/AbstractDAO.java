package com.MotorbikeStore.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.MotorbikeStore.dao.GenericDAO;
import com.MotorbikeStore.mapper.RowMapper;

public class AbstractDAO<T> implements GenericDAO<T>{
	
	
	
	
	
	protected Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/web_Sale_motorcycles";
			String user ="root";
			String passWord ="26112001hp";
			return DriverManager.getConnection(url ,user, passWord);
		} catch (ClassNotFoundException |SQLException e) {
			
			return null;
		} 
		
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connect = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connect = getConnection();
			statement = connect.prepareStatement(sql);
			//set parameter 
			setParameter(statement, parameters );
			
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				results.add(rowMapper.mapRow(resultSet));
			}
			return results;
		}catch(SQLException e) {
			return null;
		}finally {
			try {
				if(connect != null) {
					connect.close();
				}
				if(statement != null) {
					statement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			}catch(SQLException e) {
				return null;
			}
		}
			
	}

	
	private void setParameter(PreparedStatement statement, Object... parameters) {
		try {
			for(int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i]; 
				int index = i +1;
				if(parameter instanceof Long ) {
					statement.setLong(index, (Long)parameter);
				}else if(parameter instanceof Integer) {
					statement.setInt(index, (Integer)parameter );
				}else if(parameter instanceof String) {
					statement.setString(index, (String)parameter);
				}else if(parameter instanceof java.sql.Date) {
					statement.setDate(index, (java.sql.Date)parameter);
				}else if(parameter instanceof Float) {
					statement.setFloat(index, (Float)parameter);
				}else if(parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp)parameter);
				}else if(parameter == null) {
					statement.setNull(index, Types.NULL);
				}
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void update(String sql, Object... parameters) {
		
		Connection connect = null;
		PreparedStatement statement = null;
		try {
			connect = getConnection();
			connect.setAutoCommit(false);
			statement = connect.prepareStatement(sql);
			setParameter(statement, parameters);
			statement.executeUpdate();
			connect.commit();
		}catch(SQLException e) {
			if(connect != null) {
				try {
					connect.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		}finally {
			try {
				if(connect != null) {
					connect.close();
				}
				if(statement != null) {
					statement.close();
				}
				
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}

	
	public int insert(String sql, Object... parameters) {
		ResultSet resultSet = null;
		Connection connect = null;
		
		PreparedStatement statement = null;
		try {
			int id = 0;
			connect = getConnection();
			connect.setAutoCommit(false);
			statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setParameter(statement, parameters);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()) {
				id= resultSet.getInt(1);
			}
			connect.commit();
			return id;
		}catch(SQLException e) {
			if(connect != null) {
				try {
					connect.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
			
		}finally {
			try {
				if(connect != null) {
					connect.close();
				}
				if(statement != null) {
					statement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return 0;
		
	}
}
