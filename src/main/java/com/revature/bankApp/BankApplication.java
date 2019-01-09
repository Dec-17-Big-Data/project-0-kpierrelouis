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

import com.revature.dao.UsersOracle;
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
		int mainMenuInput =0;// main menu options
		int usersPage = 0;
		int index =0;
		boolean bankAppLive = true;
		UsersOracle oracleUser= new UsersOracle();
		User myUser = null;
		Account myAccount = null;
		Optional<List<User>> userList;

		while(bankAppLive)
		{
			////////////////////////////////////////////////////
			//
			//   Main menu option 1 login,2create user, 3 exit
			//
			////////////////////////////////////////////////////
			System.out.println("welcome to JDBC app");
			System.out.println("----------------------------------");
			System.out.println("what would you like to do");	
			Scanner reader = new Scanner(System.in); 
			System.out.println("press 1 for logging, press 2 to create an account, press 3 to exit: ");
			int userInput;
			mainMenuInput = reader.nextInt();
			////////////////////////////////////////
			//    login
			////////////////////////////////////////
			if(mainMenuInput==1)
			{
			System.out.println("log in page");
			System.out.println("-------------------------------");
			System.out.println("enter the user name:");
			username = reader.next();
			reader.nextLine();
			System.out.println("enter the password:");
			password = reader.next();
			reader.nextLine();
			reader.nextLine();
			//testGetAllUsers();
			userList = oracleUser.getAllUsers();	
			for(User user : userList.get())
			{
			if(user.getUsername().equals(username))
			{
			myUser = user;
			System.out.println("you have successufuly logged in");
			usersPage = 1;
			break;
			}else {
			index++;
			continue;
			}
			}
			if(index>=oracleUser.getSize())
			{
			System.out.println("your name and or password is incorrect");
			}	
			}
			/////////////////////////////////////////////////
			//
			//  create new account
			//
			////////////////////////////////////////////////
			if(mainMenuInput==2)
			{
			System.out.println("create new account page");
			System.out.println("-------------------------------");
			System.out.println();
			System.out.println("enter the user name:");
			username = reader.nextLine();
			username = reader.nextLine();
			System.out.println();
			System.out.println("enter the password:");
			password = reader.nextLine();
			
			
			
			UsersService userService = UsersService.getService();
			Optional<User> optionalUser = userService.createUser(username, password);
			if (optionalUser.isPresent()) {
			myUser = optionalUser.get();
			}
			
			System.out.println("user has been created");
			System.out.println();
			AccountsServices accountService = AccountsServices.getAccountService();
			
			try {
			Optional<Account> optionalAccount = accountService.createAccount(myUser.getUserID(),54321);
			if (optionalAccount.isPresent()) {
			myAccount = optionalAccount.get();
			
			}
			} catch (UsernameAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
			
			if(mainMenuInput==3)
			{
				bankAppLive = false;
			}
			
			///////////////////////////////////////////////////////
			//
			// users page 1 view balance, 2 deposit, 3 withdraw, 4 exit
			//
			/////////////////////////////////////////////////////
			
			if(usersPage ==1)
			{
			System.out.println("welcome: "+username);
			System.out.println();
			System.out.println(" main menu");
			System.out.println("-------------------------");
			System.out.println(" press 1 to view balance, press 2 to deposit , press 3 to withdraw, press 4 to exit to main menu");
			userInput = reader.nextInt();
			
			
			//view balance
			if(userInput ==1)
			{
			AccountsServices accountService = AccountsServices.getAccountService();
			List<Account> listOfAccounts = new ArrayList();
			Optional<List<Account>>optionalAccount = accountService.getAccount();
			if(optionalAccount.isPresent())
			{
				for(Account account: optionalAccount.get())
				{
					listOfAccounts.add(account);
				
				}
			}

				if(myUser.getUserID() == listOfAccounts.get(1).getUserID())
				{
					//System.out.println("your current balance is: "+listOfAccounts.get(1).getBalance());
					//userInput =1;
				}
			
			}
			//deposit
			if(userInput==2)
			{
			//System.out.println("your current balance is: "+listOfAccounts.get(3).getBalance());
			}
			//withdraw
			if(userInput==3)
			{
			
			}
			//exit
			if(userInput==4)
			{
				bankAppLive = false;
			}

		}

	}	
  }
}
