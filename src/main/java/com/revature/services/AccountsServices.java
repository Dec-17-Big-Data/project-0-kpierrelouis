package com.revature.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.dao.AccountsDAO;
import com.revature.dao.AccountsOracle;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.Account;

public class AccountsServices {

	
	// reference to itself
	private static AccountsServices account;
	// reference to its dao
	final static AccountsDAO accountsDAO = AccountsOracle.getDao();
	
	private AccountsServices()
	{
		
	}
	
	public static AccountsServices getAccountService()
	{
		// singleton is this is null create one
		if(account ==null)
		{
			account = new AccountsServices();
		}
		return account;
	}
	
	public Optional<List<Account>> getAccount() throws SQLException
	{
		return accountsDAO.getAllAccounts();
	}
	
	public Optional<Account> createAccount(int userID, int balance) throws UsernameAlreadyExistsException
	{
		return accountsDAO.createAccount(userID, balance);
	}
	
}
