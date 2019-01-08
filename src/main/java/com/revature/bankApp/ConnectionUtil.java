package com.revature.bankApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class ConnectionUtil {

	private static Connection connectionsInstance;
	
	private ConnectionUtil()
	{
		
	}
	
	public static Connection getConnection()
	{
		if(ConnectionUtil.connectionsInstance !=null)
		{
			return ConnectionUtil.connectionsInstance; 
		}
		InputStream in = null;
		
		try {
			 Properties props = new Properties();
             in = new FileInputStream("C:\\Users\\kevin\\Revature\\Java Workspace\\JDBCBank\\src\\main\\resources\\connections.properties");
             props.load(in);

             // get the connection object
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con = null;
             String endpoint = props.getProperty("jdbc.url");
             String username = props.getProperty("jdbc.username");
             String password = props.getProperty("jdbc.password");
             con = DriverManager.getConnection(endpoint, username, password);
             return con;
		}catch(Exception e) {
			
		}finally {
			try {
				in.close();
				//System.out.println();
			}catch (IOException e) {
				// TODO: handle exception
			}
			
		}
		return null;
	}
}
