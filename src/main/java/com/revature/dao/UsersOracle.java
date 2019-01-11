package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankApp.ConnectionUtil;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;

import oracle.jdbc.OracleTypes;

public class UsersOracle implements UsersDAO{
	
	private static UsersOracle instance;
	public static Logger log4 = LogManager.getLogger(UsersOracle.class);
	//private List<User> listOfUsers = new ArrayList<>();
	public static UsersDAO getDao() {
		if(instance==null)
		{
			instance = new UsersOracle();
		}
		return instance;
	}
	
	
	
	
	public  Optional<Integer> callCreateUser(String username, String password) 
	{
		log4.traceEntry("create user called");
		Connection con = ConnectionUtil.getConnection();
		if(con ==null)
		{
			log4.traceExit("createUser connection  fail");
			return Optional.empty();
		}
		String sql = "{call createUser(?,?,?)}";
		Integer  userId = null;
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setString(1, username);
			cstmt.setString(2, password);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.execute();
			userId = cstmt.getInt(3);
			
		}catch(SQLException e)
		{
			log4.error(e);
			return Optional.empty();
		}
		log4.traceExit("got user id"+userId);
		return Optional.of(userId);
	}
	
	public Optional<User> getUser(String userName)
	{
		log4.traceEntry("get user called");
		Connection con = ConnectionUtil.getConnection();
		if(con ==null)
		{
			log4.traceExit("get user connection  fail");
			return Optional.empty();
		}
		
		String sql = "SELECT * FROM users WHERE username = ?";
		User user = null;
		try
		{
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			user = new User(rs.getInt("user_id"), rs.getString("username"),rs.getString("password"));			
		}catch(SQLException e)
		{
			log4.error(e);
			return Optional.empty();
		}
		
		log4.traceExit("getUser "+user);
		return Optional.of(user);
	}
	
	public Optional<List<User>>getAllUsers()
	{
		log4.traceEntry("get all users called");
		Connection con = ConnectionUtil.getConnection();
		List<User> userList = new ArrayList<User>();
		if(con==null)
		{
			return Optional.empty();
		}
		String sql = "SELECT * FROM users";
		
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				userList.add(new User(rs.getInt("user_id"), rs.getString("username"),rs.getString("password")));
			}
			if(userList.isEmpty())
			{
				log4.traceExit("user list is empty");
				return Optional.empty();
			}
		}catch(SQLException e)
		{
			log4.traceExit("getAllUsers  encountered "+e);
			return Optional.empty();
		}
		return Optional.of(userList);
	}
	
	public Boolean callUpdateUser(String userName, String newName)
	{
		log4.traceEntry("callUpdateUser called");
		Connection con = ConnectionUtil.getConnection();
		
		if(con==null)
		{
			return false;
		}
		String sql = "{call updateUserName(?,?)}";
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setString(1, userName);
			cstmt.setString(2, newName);
			cstmt.execute();
			
		}catch(SQLException e)
		{
			log4.traceExit("callUpdateUser encountered "+e);
			return false;
		}
		log4.traceExit("callUpdateUpdate success");
		return true;
		
	}
	
	
	public Boolean callDeleteUser(Integer userID)
	{
		log4.traceEntry("callDeleteUser called");
		Connection con = ConnectionUtil.getConnection();
		
		if(con == null)
		{
			return false;
		}
		String sql = "{call deleteUser(?)}";
		
		try
		{
			CallableStatement cstmt = con.prepareCall(sql);
			cstmt.setInt(1, userID);
			cstmt.execute();
			
		}catch(SQLException e)
		{
			log4.traceExit("callDeleteUser encountered "+e);
			return false;
		}
		log4.traceExit("callDeleteUser success");
		return true;
	}
	
	
//	
//	@Override
//	public Optional<List<User>> getAllUsers() throws SQLException {
//		Connection con = ConnectionUtil.getConnection();
//        
//        CallableStatement cstmt = null;
//        if (con == null) {
//            return Optional.empty();
//        }
//        Boolean getAllSuccess = false;
//       // List<User> listOfUsers = new ArrayList<>();
//        try {
//            String SQL = "call getAllUsers(?)";
//            cstmt = con.prepareCall(SQL);
//            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
//            cstmt.execute();
//            ResultSet rs = (ResultSet) cstmt.getObject(1);
//            
//            while (rs.next()) {
//                listOfUsers.add(new User(rs.getInt("user_id"),rs.getString("username"),rs.getString("password"),rs.getInt("superuser")));
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
//            return Optional.of(listOfUsers);        
//        } else {
//            return Optional.empty();    
//        }
//	}
//
//	@Override
//	public Optional<User> createUser(String username, String password) throws UsernameAlreadyExistsException {
//		Connection con = ConnectionUtil.getConnection();
//        if (con == null) {
//            return Optional.empty();
//        }
//        CallableStatement cstmt = null;
//        User myUser = null;
//        try {
//           String SQL = "call CreateUser(?, ?, ?, ?)";
//           cstmt = con.prepareCall(SQL);
//           cstmt.setString(1, username);
//           cstmt.setString(2, password);
//           cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
//           cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
//           cstmt.execute();
//           Integer registerSuccess = cstmt.getInt(4);
//           if (registerSuccess==1) {
//               Integer userID = cstmt.getInt(3);
//               myUser = new User(userID,username,password,0);
//           } else {
//               throw new UsernameAlreadyExistsException("That username is already in the database.");
//           }
//          
//        } catch (SQLException e) {
//           e.printStackTrace();
//        } finally {
//            try {
//                cstmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return Optional.of(myUser);
//	}
//
//	public List<User> getListOfUsers()
//	{
//		return listOfUsers;
	//}


}
