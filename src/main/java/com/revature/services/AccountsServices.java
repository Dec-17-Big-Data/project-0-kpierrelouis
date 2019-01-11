package com.revature.services;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
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
	
	
	public Account getAccount(Integer accountID)
	{
		Account account = null;
		
		try {
			account =accountsDAO.getAccount(accountID).get();
			
		}catch(NoSuchElementException e)
		{
			return null;
		}
		if(account==null)
		{
			return null;
		}
		
		return account;
		
		
	}
	
	
	
	public Integer createAccount(Integer userId)
	{
		Integer account = null;
		try
		{
			account = accountsDAO.callCreateAccount(userId).get();
			
		}catch(NoSuchElementException e)
		{
			return null;
		}
		
		if(account==null)
		{
			return null;
		}
		
		return account;
	}
	
	
	
	
	public Boolean callWithdraw(Integer balance,Integer accountID ) 
	{
		if(balance<=0)
		{
			return false;
		}
		
		if(getAccount(accountID)==null)
		{
			return false;
		}
		if(accountsDAO.callWithdraw(balance, accountID) == false)
		{
			return false;
		}
		return true;
	}
	
	public  Boolean callDeposit(Integer balance,Integer accountID)
	{
		if(balance<=0)
		{
			return false;
		}
		
		if(getAccount(accountID)==null)
		{
			return false;
		}
		if(accountsDAO.callDeposit(balance, accountID) == false)
		{
			return false;
		}
		return true;
	}
	
	public Boolean deleteAccount(Integer accountID)
	{
		
		if(getAccount(accountID)==null)
		{
			return false;
		}
		if(accountsDAO.deleteAccount( accountID) == false)
		{
			return false;
		}
		
		return true;
	}
	
	
//	
//	public Optional<List<Account>> getAccount() throws SQLException
//	{
//		return accountsDAO.getAllAccounts();
//	}
//	
//	public Optional<Account> createAccount(int userID, int balance) throws UsernameAlreadyExistsException
//	{
//		return accountsDAO.createAccount(userID, balance);
//	}
//	
}
