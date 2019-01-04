package com.revature.bankApp;

import java.io.Serializable;

public class BalancesModel implements Serializable{

	private int balanceID;
	private double balanceAmount;
	
	public BalancesModel(int id, double amount)
	{
		balanceID = id;
		balanceAmount = amount;
	}
	
	public int getBalanceID()
	{
		return balanceID;
	}
	
	public double getAmount()
	{
		return balanceAmount;
				
	}
	
	public void setBalanceID(int id)
	{
		balanceID = id;
	}
	
	public void setAmount(double amount)
	{
		balanceAmount = amount;
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
