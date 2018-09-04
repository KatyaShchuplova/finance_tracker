package com.shchuplov.ek.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class RateDbUtil {
	
	private DataSource dataSource;

	public RateDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<UsdRate> getRates() throws Exception {
		
		List<UsdRate> rates = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String sql = "select * from currency order by date";
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				Date date = resultSet.getDate("date");
				double rate = resultSet.getDouble("usd_rate");
				UsdRate tempRate = new UsdRate(date, rate);
				rates.add(tempRate);				
			}
			
		} finally {
			close(connection, statement, resultSet);
		}
		
		return rates;
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private java.sql.Date getSqlDate(String day, SimpleDateFormat format) throws Exception {
        Date parsed = format.parse(day);
        return new java.sql.Date(parsed.getTime());
	}

	public void add(String date, String rateOnDay) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {		
			connection = dataSource.getConnection();
			String sql = "insert into currency "
					   + "(date, usd_rate) "
					   + "values(?, ?)";
			statement = connection.prepareStatement(sql);
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			statement.setDate(1, getSqlDate(date, format));
			statement.setDouble(2, Double.parseDouble(rateOnDay));
			statement.executeUpdate();
			
		} finally {

			close(connection, statement, null);		
		}
				
	}

	public void deleteRate(String date) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {	
			connection = dataSource.getConnection();
			String sql = "delete from currency where date=?";
			statement = connection.prepareStatement(sql);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			statement.setDate(1, getSqlDate(date, format));
			statement.execute();
		} finally {
			close(connection, statement, null);
		}
		
	}

	boolean canAddDate(String date) throws Exception {
		
		if (!isExistDate(date)) {
			Date currentDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date theDateToAdd = format.parse(date);
			if (theDateToAdd.before(currentDate)) {
				return true;
			}
		}
		return false;
	}
	
	boolean isExistDate(String date) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		boolean isDateExist = false;
		
		try {
			connection = dataSource.getConnection();
			String sql = "select 1 from currency where date = ?";
			statement = connection.prepareStatement(sql);
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			statement.setDate(1, getSqlDate(date, format));	
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				isDateExist = true;			
			}
			
		} finally {
			close(connection, statement, resultSet);
		}
				
		return isDateExist;
	}
	
}
