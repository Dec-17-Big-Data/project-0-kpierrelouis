package com.revature.bankApp;

import java.io.Serializable;

public class ServicesModel implements Serializable{

	private int serviceId;
	private double withdrawAmount;
	private double depositAmount;
	
	public ServicesModel(int id, double withdraw, double deposit)
	{
		serviceId = id;
		withdrawAmount = withdraw;
		deposit = deposit;
	}
	
	public int getId()
	{
		return 0;
	}
	
	public double getWithdraw()
	{
		return 0;
	}
	
	public void setId(int id)
	{
		serviceId = id;
	}
	
	public void setDeposit(double deposit)
	{
		depositAmount = deposit;
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
