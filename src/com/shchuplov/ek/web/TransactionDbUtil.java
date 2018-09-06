package com.shchuplov.ek.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class TransactionDbUtil {
	
	private DataSource dataSource;

	public TransactionDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Transaction> getTransactions() throws Exception {
		
		List<Transaction> transactions = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String sql = "select * from transaction order by date";
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				Date date = resultSet.getDate("date");
				TypeTransaction typeTransaction = TypeTransaction.valueOf(resultSet.getString("type_transaction"));
				
				double transactionAmountUsd = resultSet.getDouble("transaction_amount_usd");
				double transactionRate = resultSet.getDouble("transaction_rate");
				
				Transaction tempTransaction = new Transaction(date, typeTransaction, transactionAmountUsd, transactionRate);
				transactions.add(tempTransaction);				
			}
			
		} finally {
			close(connection, statement, resultSet);
		}
		
		return transactions;
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
	
/*
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
	}*/
	
}
