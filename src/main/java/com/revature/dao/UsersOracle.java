package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.bankApp.ConnectionUtil;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;

import oracle.jdbc.OracleTypes;

public class UsersOracle implements UsersDAO{
	
	private static UsersOracle instance;

	public static UsersDAO getDao() {
		if(instance==null)
		{
			instance = new UsersOracle();
		}
		return instance;
	}
	
	@Override
	public Optional<List<User>> getAllUsers() throws SQLException {
		Connection con = ConnectionUtil.getConnection();
        
        CallableStatement cstmt = null;
        if (con == null) {
            return Optional.empty();
        }
        Boolean getAllSuccess = false;
        List<User> listOfUsers = new ArrayList<>();
        try {
            String SQL = "call getAllUsers(?)";
            cstmt = con.prepareCall(SQL);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            ResultSet rs = (ResultSet) cstmt.getObject(1);
            
            while (rs.next()) {
                listOfUsers.add(new User(rs.getInt("user_id"),rs.getString("username"),rs.getString("password"),rs.getInt("superuser")));
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
            return Optional.of(listOfUsers);        
        } else {
            return Optional.empty();    
        }
	}

	@Override
	public Optional<User> createUser(String username, String password) throws UsernameAlreadyExistsException {
		Connection con = ConnectionUtil.getConnection();
        if (con == null) {
            return Optional.empty();
        }
        CallableStatement cstmt = null;
        User myUser = null;
        try {
           String SQL = "call CreateUser(?, ?, ?, ?)";
           cstmt = con.prepareCall(SQL);
           cstmt.setString(1, username);
           cstmt.setString(2, password);
           cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
           cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
           cstmt.execute();
           Integer registerSuccess = cstmt.getInt(4);
           if (registerSuccess==1) {
               Integer userID = cstmt.getInt(3);
               myUser = new User(userID,username,password,0);
           } else {
               throw new UsernameAlreadyExistsException("That username is already in the database.");
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
        return Optional.of(myUser);
	}



}
