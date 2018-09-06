package com.shchuplov.ek.web;

import java.util.Date;

public class Transaction {
	
	private int id;
	private Date date; 
	private TypeTransaction typeTransaction;
	private Double transactionAmountUsd;
	private Double transactionRate;
	
	public Transaction(int id, Date date, TypeTransaction typeTransaction, 
					   Double transactionAmountUsd, Double transactionRate) {
		this.id = id;
		this.date = date;
		this.typeTransaction = typeTransaction;
		this.transactionAmountUsd = transactionAmountUsd;
		this.transactionRate = transactionRate;
	}
	
	public Transaction(Date date, TypeTransaction typeTransaction, Double transactionAmountUsd, Double transactionRate) {
		this.date = date;
		this.typeTransaction = typeTransaction;
		this.transactionAmountUsd = transactionAmountUsd;
		this.transactionRate = transactionRate;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public Double getTransactionAmountUsd() {
		return transactionAmountUsd;
	}

	public void setTransactionAmountUsd(Double transactionAmountUsd) {
		this.transactionAmountUsd = transactionAmountUsd;
	}

	public Double getTransactionRate() {
		return transactionRate;
	}

	public void setTransactionRate(Double transactionRate) {
		this.transactionRate = transactionRate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", typeTransaction=" + typeTransaction
				+ ", transactionAmountUsd=" + transactionAmountUsd + ", transactionRate=" + transactionRate + "]";
	}
	

}
