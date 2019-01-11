package com.revature.models;

import java.io.Serializable;



public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2904630994560200224L;
	private int accountId;
	private int userID;
	private int balance;
	
	public Account( int accountid,int userID, int balance) {
		super();
		this.accountId = accountid;
		this.userID = userID;
		this.balance = balance;
	}
	

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + balance;
		result = prime * result + userID;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (balance != other.balance)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", userID=" + userID + ", balance=" + balance + "]";
	}
	
	
	
	
	
	
	
	
	
}
