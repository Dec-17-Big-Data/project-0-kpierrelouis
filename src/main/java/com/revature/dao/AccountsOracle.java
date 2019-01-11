package com.revature.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankApp.ConnectionUtil;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.Account;
import com.revature.models.User;

import oracle.jdbc.OracleTypes;

public class AccountsOracle implements AccountsDAO{

	private static AccountsOracle instance;
	public Logger log4 = LogManager.getLogger(AccountsOracle.class);
	// all oracles return their DAOs
	public static AccountsDAO getDao()
	{
		if(instance==null)
		{
			instance = new AccountsOracle();
		}
		
		return instance;
	}
	
	public Optional<List<Account>>getAllAccounts(Integer userID)
	{
		log4.traceEntry("getAllAccounts called");
		Connection con = ConnectionUtil.getConnection();
		List<Account> accountList = new ArrayList<Account>();
		
		if(con==null)
		{
			log4.traceExit("getAllAccounts connection faileds");
			return Optional.empty();
		}
		
		String sql = "SELECT * FROM accounts WHERE user_id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				accountList.add(new Account(rs.getInt("account_id"),rs.getInt("user_id"),rs.getInt("balance")));
			}
			if(accountList.isEmpty())
			{
				log4.traceExit("accountList is empty");
				return Optional.empty();
			}	
		}catch(SQLException e)
		{
			log4.traceExit("getAllAccounts encountered "+e);
			return Optional.empty();
		}
		return Optional.of(accountList);
	}
	
	
	public Optional<Integer> callCreateAccount(Integer userId)
	{
		log4.traceEntry("calledCreateAccount");
		Connection con = ConnectionUtil.getConnection();
		Integer accountId = null;
		if(con == null)
		{
			log4.traceExit("connection falled");
			return Optional.empty();
		}
		String sql = "{call createAccount(?,?)}";
		try {
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setInt(1, userId);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.execute();
			accountId = cstmt.getInt(2);
		} catch (SQLException e) {
			
			log4.traceExit("callCreateAccount encounterd "+e);
			return Optional.empty();
		}
		log4.traceExit("callCreateAccount success");
		return Optional.of(accountId);
		
	}
	
	public Optional<Account> getAccount(Integer accountID)
	{

		log4.traceEntry("calledCreateAccount");
		Connection con = ConnectionUtil.getConnection();
		Account account= null;
		if(con == null)
		{
			log4.traceExit("connection falled");
			return Optional.empty();
		}
		String sql = "SELECT * FROM accounts WHERE account_id =?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			account = new Account(rs.getInt("account_id"), rs.getInt("user_id"),rs.getInt("balance"));
		}catch(SQLException e)
		{
			log4.traceExit("getAccount encountered "+e);
			return Optional.empty();
		}
		log4.traceExit("get Account success");
		return Optional.of(account);
	}
	
	public Boolean deleteAccount(Integer accountID)
	{

		log4.traceEntry("calledDeleteAccount");
		Connection con = ConnectionUtil.getConnection();
		Integer accountId = null;
		if(con == null)
		{
			log4.traceExit("connection falled");
			return false;
		}
		String sql = "{call deleteAccount(?)}";
		
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setInt(1, accountID);
			cstmt.execute();
		}catch(SQLException e)
		{
			log4.traceExit("deleteAccount encountered "+e);
			return false;
		}
		log4.traceExit("deleteAccount success");
		return true;
	}
	
	public Boolean callWithdraw(Integer balance,Integer accountID ) 
	{

		log4.traceEntry("calledWithdraw");
		Connection con = ConnectionUtil.getConnection();
		
		if(con == null)
		{
			log4.traceExit("connection falled");
			return false;
		}
		String sql = "{call withdraw(?,?)}";
	
		
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setInt(1,balance);
			cstmt.setInt(2, accountID);
			cstmt.execute();
		}catch(SQLException e)
		{
			log4.traceExit("withdraw encountered "+e);
			return false;
		}
		log4.traceExit("withdraw success");
		return true;
		
	}
	public  Boolean callDeposit(Integer balance,Integer accountID)
	{

		log4.traceEntry("calledDeposit");
		Connection con = ConnectionUtil.getConnection();
		
		if(con == null)
		{
			log4.traceExit("connection falled");
			return false;
		}
		String sql = "{call deposit(?,?)}";
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setInt(1,balance);
			cstmt.setInt(2, accountID);
			cstmt.execute();
		}catch(SQLException e)
		{
			log4.traceExit("callDeposit encountered "+e);
			return false;
		}
		log4.traceExit("callDeposit success");
		return true;
	}

	

//	@Override
//	public Optional<List<Account>> getAllAccounts() throws SQLException {
//		Connection con = ConnectionUtil.getConnection();
//        
//        CallableStatement cstmt = null;
//        if (con == null) {
//            return Optional.empty();
//        }
//        Boolean getAllSuccess = false;
//        List<Account> listOfAccounts = new ArrayList<>();
//        try {
//            String SQL = "call getAllAccounts(?)";
//            cstmt = con.prepareCall(SQL);
//            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
//            cstmt.execute();
//            ResultSet rs = (ResultSet) cstmt.getObject(1);
//            
//            while (rs.next()) {
//                listOfAccounts.add(new Account(rs.getInt("account_id"),rs.getInt("user_id"),rs.getInt("balance")));
//            }
//            getAllSuccess = true;
//        } catch (SQLException e) {
//           // logger.catching(e);
//            getAllSuccess = false;
//        } finally {
//            try {
//                cstmt.close();
//            } catch (SQLException e) {
//               // logger.catching(e);
//            }
//        }
//        if (getAllSuccess) {
//            return Optional.of(listOfAccounts);        
//        } else {
//            return Optional.empty();    
//        }
//	}
//
//
//	@Override
//	public Optional<Account> createAccount(int userId, int accountBalance) throws UsernameAlreadyExistsException{
//	Connection con = ConnectionUtil.getConnection();
//    if (con == null) {
//        return Optional.empty();
//    }
//    CallableStatement cstmt = null;
//    Account myAccount = null;
//    try {
//       String SQL = "call createAccount(?,?,?,?)";
//       cstmt = con.prepareCall(SQL);
//       cstmt.setInt(1, userId);
//       cstmt.setInt(2, accountBalance);
//       cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
//       cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
//       cstmt.execute();
//       Integer registerSuccess = cstmt.getInt(4);
//       if (registerSuccess==1) {
//           Integer accountId = cstmt.getInt(3);
//           myAccount = new Account(accountId,userId,accountBalance);
//       } else {
//           throw new UsernameAlreadyExistsException("That account is  already in the database.");
//       }
//      
//    } catch (SQLException e) {
//       e.printStackTrace();
//    } finally {
//        try {
//            cstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    return Optional.of(myAccount);
//			
//	}
//	
//	public Optional<Integer> depositToAccount(int accountBalance, int accountid, int depositAmount) throws SQLException
//	{
//		Connection con = ConnectionUtil.getConnection();
//	    if (con == null) {
//	        return Optional.empty();
//	    }
//	    CallableStatement cstmt = null;
//		
//		try
//		{
//			String SQL = "call deposit(?,?)";
//			cstmt = con.prepareCall(SQL);
//			
//			cstmt.setInt(1, depositAmount);
//			cstmt.setInt(2, accountid);
//			
//			//cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
//			//cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
//			cstmt.execute();
//			//Integer registerSuccess = cstmt.getInt(1);
//			Integer registerSuccess = 1;
//			if (registerSuccess==1) {
//		          // depositAmount = cstmt.getInt(1)+depositAmount;
//				//Account myAccount = new Account(cstmt.getInt(1),cstmt.getInt(2),accountBalance);
//		       } else {
//		           throw new SQLException("cant deposit amount.");
//		       }
//			
//		}catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//		
//		return Optional.of(depositAmount);
//	}
	

}
