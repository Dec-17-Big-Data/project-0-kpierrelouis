package com.revature.services;

import java.sql.SQLException;
import java.util.List;
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
	
	public Optional<List<User>> getAllUsers() throws SQLException {
		return usersDAO.getAllUsers();
	}
	
	public Optional<User> createUser(String username, String password) throws UsernameAlreadyExistsException {
		return usersDAO.createUser(username, password);
	}
}
