package com.revature.bankApp;

import java.io.Serializable;

public class UsersModel implements Serializable{

	private String firstName;
	private String lastName;
	private int userId;
	
	public UsersModel(String first, String last, int id)
	{
		firstName = first;
		lastName = last;
		userId = id;
	}
	
	public String getFirst()
	{
		return null;
	}
	
	public String getLast()
	{
		return null;
	}
	
	public int getId()
	{
		return 0;
	}
	
	public void setFirst(String first)
	{
		firstName = first;
	}
	
	public void setLast(String last)
	{
		lastName = last;
	}
	
	public void setId(int id)
	{
		userId = id;
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
