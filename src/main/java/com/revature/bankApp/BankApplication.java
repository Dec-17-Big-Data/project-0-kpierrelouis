package com.revature.bankApp;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UsersService;
import com.revature.models.Account;
import com.revature.services.AccountsServices;

public class BankApplication {

	private static final String lastName = null;

	public static void main(String[] args) throws UsernameAlreadyExistsException, SQLException {
		String username="";
		String password ="";
		int mainMenuInput =0;
		//testCreateUser();
		boolean bankAppLive = true;
		//testCreateAccount();
		//testGetUsers();
		//testGetAllUsers();
		//testGetAllAccounts();
		
		
//		
			System.out.println("welcome to my bank app");
			System.out.println("----------------------------------");
			System.out.println("what would you like to do");	
			Scanner reader = new Scanner(System.in); 
			System.out.println("press 1 for loging, press 2 to create account, press 3 to exit: ");
			mainMenuInput = reader.nextInt();
			//login
			if(mainMenuInput==1)
			{
				System.out.println("log in page");
				System.out.println("-------------------------------");
				System.out.println("enter the user name:");
				username = reader.toString();
				reader.nextLine();
				reader.nextLine();
				System.out.println("enter the password:");
				password = reader.toString();
				reader.nextLine();
				reader.nextLine();
			}
			
			if(mainMenuInput==2)
			{
				System.out.println("create new account page");
				System.out.println("-------------------------------");
				System.out.println("enter the user name:");
				username = reader.nextLine();
				username = reader.nextLine();
			
				System.out.println("enter the password:");
				password = reader.nextLine();
				
				
				
				
				testCreateUser(username,password);
				System.out.println("user has been created");
				
			}
			
			if(mainMenuInput ==3)
			{
				System.out.println("thank you for using our bank");
			}
			
			switch(mainMenuInput)
			{
			case 1://log in

			case 2://create account
			
				break;
			case 3://exit
				break;
			}
			
			reader.close();

		
		
	}
	
	
	
	/*public static void testUserService() {
		UsersService userService = UsersService.getService();
		List<User> listOfUsers = new ArrayList<>();
	}*/
	public static void testGetAllAccounts()throws UsernameAlreadyExistsException, SQLException
	{
		AccountsServices accountService = AccountsServices.getAccountService();
		List<Account> listOfAccounts = new ArrayList();
		Optional<List<Account>>optionalAccount = accountService.getAccount();
		if(optionalAccount.isPresent())
		{
			for(Account account: optionalAccount.get())
			{
				listOfAccounts.add(account);
				//System.out.println(account.getAccountId()+" "+account.getUserID()+" "+account.getBalance());
				System.out.println(account);
			}
		}
	}
//	public static void testCreateUser() {
//		UsersService userService = UsersService.getService();
//		List<User> listOfUsers = new ArrayList<>();
//		try {
//			Optional<User> optionalUser = userService.createUser("jason", "smith");
//			if (optionalUser.isPresent()) {
//				User myUser = optionalUser.get();
//				System.out.println(myUser.getUserID());
//				System.out.println(myUser.getUsername());
//				System.out.println(myUser.getPassword());
//				System.out.println(myUser.getSuperUser());
//			}
//		} catch (UsernameAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	public static void testCreateUser(String userName,String passWord) {
		UsersService userService = UsersService.getService();
		List<User> listOfUsers = new ArrayList<>();
		try {
			Optional<User> optionalUser = userService.createUser(userName, passWord);
			if (optionalUser.isPresent()) {
				User myUser = optionalUser.get();
				System.out.println(myUser.getUserID());
				System.out.println(myUser.getUsername());
				System.out.println(myUser.getPassword());
				System.out.println(myUser.getSuperUser());
			}
		} catch (UsernameAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testGetAllUsers()throws UsernameAlreadyExistsException, SQLException
	{
		UsersService userService = UsersService.getService();
		List<User> listOfUsers = new ArrayList();
		Optional<List<User>>optionalUser = userService.getAllUsers();
		if(optionalUser.isPresent())
		{
			for(User user : optionalUser.get())
			{
				listOfUsers.add(user);
				System.out.println(user);
			}
		}
	}
	
	public static void testAccountService()
	{
		AccountsServices accountService = AccountsServices.getAccountService();
		List<Account> listOfAccounts = new ArrayList();	
	}
	
	public static void testCreateAccount()
	{
		AccountsServices accountService = AccountsServices.getAccountService();
//		List<Account> listOfAccounts = new ArrayList<>();
		try {
			Optional<Account> optionalAccount = accountService.createAccount(1,54321);
			if (optionalAccount.isPresent()) {
				Account myAccount = optionalAccount.get();
				System.out.println(myAccount.getAccountId());
				System.out.println(myAccount.getBalance());
				System.out.println(myAccount.getUserID());
			}
		} catch (UsernameAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
