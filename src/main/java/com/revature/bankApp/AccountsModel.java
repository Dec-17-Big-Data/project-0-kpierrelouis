package com.revature.bankApp;

import java.io.Serializable;

public class AccountsModel implements Serializable{

	private int accountId;
	private double accountBalance;
	private int accountNumber;
	
	public AccountsModel(int theAccount, double balance, int num)
	{
		accountId = theAccount;
		accountBalance = balance;
		accountNumber = num;
	}
	
	public int getId()
	{
		return 0;
	}
	
	public double getBalance()
	{
		return 0;
	}
	
	public int getNum()
	{
		return 0;
	}
	
	public void setBalance(double balance)
	{
		accountBalance = balance;
	}
	
	public void setNum(int theNum)
	{
		accountNumber = theNum;
	}
	
	public int hashCode()
	{
		return 0;
	}
	
	public boolean equals()
	{
		return false;
	}
	
}
