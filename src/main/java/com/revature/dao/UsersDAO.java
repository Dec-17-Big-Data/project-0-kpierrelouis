package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;

public interface UsersDAO {
	 Optional<List<User>> getAllUsers() throws SQLException;
	 Optional<User> createUser(String username, String password) throws UsernameAlreadyExistsException;
}
