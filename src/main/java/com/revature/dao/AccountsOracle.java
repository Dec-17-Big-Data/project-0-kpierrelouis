package com.revature.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Optional;

import com.revature.bankApp.ConnectionUtil;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.Account;
import com.revature.models.User;

import oracle.jdbc.OracleTypes;

public class AccountsOracle implements AccountsDAO{

	private static AccountsOracle instance;

	// all oracles return their DAOs
	public static AccountsDAO getDao()
	{
		if(instance==null)
		{
			instance = new AccountsOracle();
		}
		return instance;
	}

	@Override
	public Optional<List<Account>> getAllAccounts() throws SQLException {
		Connection con = ConnectionUtil.getConnection();
        
        CallableStatement cstmt = null;
        if (con == null) {
            return Optional.empty();
        }
        Boolean getAllSuccess = false;
        List<Account> listOfAccounts = new ArrayList<>();
        try {
            String SQL = "call getAllAccounts(?)";
            cstmt = con.prepareCall(SQL);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            ResultSet rs = (ResultSet) cstmt.getObject(1);
            
            while (rs.next()) {
                listOfAccounts.add(new Account(rs.getInt("account_id"),rs.getInt("user_id"),rs.getInt("balance")));
            }
            getAllSuccess = true;
        } catch (SQLException e) {
           // logger.catching(e);
            getAllSuccess = false;
        } finally {
            try {
                cstmt.close();
            } catch (SQLException e) {
               // logger.catching(e);
            }
        }
        if (getAllSuccess) {
            return Optional.of(listOfAccounts);        
        } else {
            return Optional.empty();    
        }
	}


	@Override
	public Optional<Account> createAccount(int userId, int accountBalance) throws UsernameAlreadyExistsException{
	Connection con = ConnectionUtil.getConnection();
    if (con == null) {
        return Optional.empty();
    }
    CallableStatement cstmt = null;
    Account myAccount = null;
    try {
       String SQL = "call createAccount(?,?,?,?)";
       cstmt = con.prepareCall(SQL);
       cstmt.setInt(1, userId);
       cstmt.setInt(2, accountBalance);
       cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
       cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
       cstmt.execute();
       Integer registerSuccess = cstmt.getInt(4);
       if (registerSuccess==1) {
           Integer accountId = cstmt.getInt(3);
           myAccount = new Account(accountId,userId,accountBalance);
       } else {
           throw new UsernameAlreadyExistsException("That account is  already in the database.");
       }
      
    } catch (SQLException e) {
       e.printStackTrace();
    } finally {
        try {
            cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return Optional.of(myAccount);
			
	}
	
	public Optional<Integer> depositToAccount(int accountBalance, int accountid) throws SQLException
	{
		Connection con = ConnectionUtil.getConnection();
	    if (con == null) {
	        return Optional.empty();
	    }
	    CallableStatement cstmt = null;
		 int depositAmount = 0;
		try
		{
			String SQL = "call createAccount(?)";
			cstmt = con.prepareCall(SQL);
			cstmt.getInt(accountid);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			Integer registerSuccess = cstmt.getInt(1);
			if (registerSuccess==1) {
		           depositAmount= cstmt.getInt(1);
		          // myAccount = new Account(accountId,userId,accountBalance);
		       } else {
		           throw new SQLException("cant deposit amount.");
		       }
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return Optional.of(depositAmount);
	}
	

}
