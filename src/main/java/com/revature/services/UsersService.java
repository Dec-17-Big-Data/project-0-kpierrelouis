package com.revature.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.revature.dao.UsersDAO;
import com.revature.dao.UsersOracle;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;

public class UsersService {

	private static UsersService userService;
	final static UsersDAO usersDAO = UsersOracle.getDao();
	
	private UsersService() {}
	
	public static UsersService getService() {
		if(userService==null) {
			userService = new UsersService();
		}
		return userService;
	}
	
	public List<User> getAllUsers()  {
		List<User> userList = new ArrayList<User>();
		try {
			userList =  usersDAO.getAllUsers().get();
		}catch(NoSuchElementException e)
		{
			return null;
		}
		
		if(userList.isEmpty())
		{
			return null;
		}
		return userList;
	}
	
	public  Integer createUser(String username, String password){
		Integer userID = null;
		
		try
		{
			userID = usersDAO.callCreateUser(username, password).get();
			
		}catch(NoSuchElementException e)
		{
			return null;
		}
		return userID;
		 
	}
	
	
	public User getUser(String userName)
	{
		User user = null;
		
		try
		{
			user = usersDAO.getUser(userName).get();
		}catch(NoSuchElementException e)
		{
			return null;
		}
		return user;
	}
	
	public Boolean callUpdateUser(String userName, String newName)
	{
		if(getUser(userName) == null)
		{
			return false;
		}
		
		if(getUser(newName)!=null)
		{
			return false;
		}
		
		if(usersDAO.callUpdateUser(userName, userName)==false)
		{
			return false;
		}
		if(getUser(newName)==null)
		{
			return false;
		}
		if(getUser(userName)!=null)
		{
			return false;
		}
		
		return true;	
	}
	
	public Boolean callDeleteUser(String userName)
	{
		User user = getUser(userName);
		
		if(user==null)
		{
			return false;
		}
		
		if(usersDAO.callDeleteUser(user.getUserID())==false)
		{
			return false;
		}
		
		if(getUser(userName) == null)
		{
			return false;
		}
		return true;
	}
	
}
