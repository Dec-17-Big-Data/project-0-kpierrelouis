package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.Account;

public interface AccountsDAO {

	public Optional<List<Account>>getAllAccounts(Integer userID);
	public Optional<Integer> callCreateAccount(Integer userId);
	public Optional<Account> getAccount(Integer accountID);
	public Boolean deleteAccount(Integer accountID);
	public Boolean callWithdraw(Integer balance,Integer accountID);
	public Boolean callDeposit(Integer balance,Integer accountID);
	
	
	
}
