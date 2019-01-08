package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.Account;

public interface AccountsDAO {

	Optional<List<Account>>getAllAccounts() throws SQLException;
	Optional<Account> createAccount(int userId, int accountBalance)  throws UsernameAlreadyExistsException;
	
}
