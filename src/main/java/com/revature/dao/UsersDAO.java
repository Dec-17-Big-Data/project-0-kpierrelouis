package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;

public interface UsersDAO {
	public  Optional<Integer> callCreateUser(String username, String password);
	public Optional<User> getUser(String userName);
	public Boolean callUpdateUser(String userName, String newName);
	public Boolean callDeleteUser(Integer userID);
	public Optional<List<User>> getAllUsers();
	 
}
