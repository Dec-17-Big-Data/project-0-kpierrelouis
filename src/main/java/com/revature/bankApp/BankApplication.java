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

import com.revature.dao.AccountsOracle;
import com.revature.dao.UsersOracle;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UsersService;
import com.revature.models.Account;
import com.revature.services.AccountsServices;

public class BankApplication {

	private static final String lastName = null;
	static enum  states  {start,logging,createUser,user,deposit,withdraw,deleteAccount};
	static states state = states.start;
	static Boolean admin= false;
	static Boolean isLoggedIn = false;
	static boolean bankAppLive = true;
	static Scanner reader = new Scanner(System.in);
	static UsersService userService ;
	static AccountsServices accountService;
	static User currentUser;
	public static void main(String[] args) throws UsernameAlreadyExistsException, SQLException {
		userService = UsersService.getService();
		accountService = AccountsServices.getAccountService();
		while(bankAppLive)
		{
			view();
			String input = reader.nextLine();
			switch(state)
			{
			case start:
				startOptions(input);
				break;
			case logging:
				if(input.equals("return")) {
					switchState(states.start);
				}else {
					loggingOptions(input,reader);
				}
				break;
			case createUser:
				if(input.equals("return")) {
					switchState(states.start);
				}else {
					createUserOptions(input,reader);
				}
				break;
			case user:
				if(input.equals("return")) {
					switchState(states.start);
					currentUser = null;
					isLoggedIn = false;
				}else {
					userOptions(input);
				}
				break;
			case deposit:
				if(input.equals("return")) {
					switchState(states.user);
				}
				else {
					depositOptions(input);
				}
				break;
			case withdraw:
				if(input.equals("return")) {
					switchState(states.user);
				}
				else {
					withdrawOptions(input);
				}
				break;
			case deleteAccount:
				if(input.equals("return")) {
					switchState(states.user);
				}
				else {
					deleteAccountOptions(input);
				}
				break;
				
				
			default:
				break;
			}
		}
		
		
		
		
//		String username="";
//		String password ="";
//		int mainMenuInput =0;// main menu options
//		int usersPage = 0;
//		int index =0;
//		boolean bankAppLive = true;
//		UsersOracle oracleUser= new UsersOracle();
//		User myUser = null;
//		Account myAccount = null;
//		Optional<List<User>> userList;
//		
		//testDeposit();
//		while(bankAppLive)
//		{
//			////////////////////////////////////////////////////
//			//
//			//   Main menu option 1 login,2create user, 3 exit
//			//
//			////////////////////////////////////////////////////
//			System.out.println("welcome to JDBC app");
//			System.out.println("----------------------------------");
//			System.out.println("what would you like to do");	
//			Scanner reader = new Scanner(System.in); 
//			System.out.println("press 1 for logging, press 2 to create an account, press 3 to exit: ");
//			int userInput;
//			mainMenuInput = reader.nextInt();
//			////////////////////////////////////////
//			//    login
//			////////////////////////////////////////
//			if(mainMenuInput==1)
//			{
//			System.out.println("log in page");
//			System.out.println("-------------------------------");
//			System.out.println("enter the user name:");
//			username = reader.next();
//			reader.nextLine();
//			System.out.println("enter the password:");
//			password = reader.next();
//			reader.nextLine();
//			reader.nextLine();
//			//testGetAllUsers();
//			userList = oracleUser.getAllUsers();	
//			for(User user : userList.get())
//			{
//			if(user.getUsername().equals(username))
//			{
//			myUser = user;
//			System.out.println("you have successufuly logged in");
//			usersPage = 1;
//			break;
//			}else {
//			index++;
//			continue;
//			}
//			}
//			if(index>=oracleUser.getSize())
//			{
//			System.out.println("your name and or password is incorrect");
//			}	
//			}
//			/////////////////////////////////////////////////
//			//
//			//  create new account
//			//
//			////////////////////////////////////////////////
//			if(mainMenuInput==2)
//			{
//			System.out.println("create new account page");
//			System.out.println("-------------------------------");
//			System.out.println();
//			System.out.println("enter the user name:");
//			username = reader.nextLine();
//			username = reader.nextLine();
//			System.out.println();
//			System.out.println("enter the password:");
//			password = reader.nextLine();
//			
//			
//			
//			UsersService userService = UsersService.getService();
//			Optional<User> optionalUser = userService.createUser(username, password);
//			if (optionalUser.isPresent()) {
//			myUser = optionalUser.get();
//			}
//			
//			System.out.println("user has been created");
//			System.out.println();
//			AccountsServices accountService = AccountsServices.getAccountService();
//			
//			try {
//			Optional<Account> optionalAccount = accountService.createAccount(myUser.getUserID(),54321);
//			if (optionalAccount.isPresent()) {
//			myAccount = optionalAccount.get();
//			
//			}
//			} catch (UsernameAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
//			}
//			
//			if(mainMenuInput==3)
//			{
//				bankAppLive = false;
//			}
//			
//			///////////////////////////////////////////////////////
//			//
//			// users page 1 view balance, 2 deposit, 3 withdraw, 4 exit
//			//
//			/////////////////////////////////////////////////////
//			
//			if(usersPage ==1)
//			{
//			System.out.println("welcome: "+username);
//			System.out.println();
//			System.out.println(" main menu");
//			System.out.println("-------------------------");
//			System.out.println(" press 1 to view balance, press 2 to deposit , press 3 to withdraw, press 4 to exit to main menu");
//			userInput = reader.nextInt();
//			
//			
//			//view balance
//			if(userInput ==1)
//			{
//			AccountsServices accountService = AccountsServices.getAccountService();
//			List<Account> listOfAccounts = new ArrayList();
//			Optional<List<Account>>optionalAccount = accountService.getAccount();
//			if(optionalAccount.isPresent())
//			{
//				for(Account account: optionalAccount.get())
//				{
//					listOfAccounts.add(account);
//				
//				}
//			}
//
//				if(myUser.getUserID() == listOfAccounts.get(1).getUserID())
//				{
//					//System.out.println("your current balance is: "+listOfAccounts.get(1).getBalance());
//					//userInput =1;
//				}
//			
//			}
//			//deposit
//			if(userInput==2)
//			{
//			//System.out.println("your current balance is: "+listOfAccounts.get(3).getBalance());
//			}
//			//withdraw
//			if(userInput==3)
//			{
//			
//			}
//			//exit
//			if(userInput==4)
//			{
//				bankAppLive = false;
//			}
//
//		}
//
//	}	
  }
	public static void view()
	{
		switch(state)
		{
		case start:
			System.out.println("welcome to the bank");
			System.out.println("select options:");
			System.out.println("1 logging");
			System.out.println("2 create user");
			System.out.println("3 exit");
			break;
		case logging:
			System.out.println("Logging In");
			System.out.println("please enter your username ");
			break;
		case createUser:
			System.out.println("Creating User");
			System.out.println(" please enter a new user name ");
			break;
		case user:
			System.out.println("please select opitons");
			System.out.println("1 view all accounts");
			System.out.println("2 create account");
			System.out.println("3 deposit ");
			System.out.println("4  withdraw");
			System.out.println("5 delete");
			System.out.println("6 log out");
			break;
		case deposit:
			System.out.println("how much would you like to deposit:");
			break;
		case withdraw:
			System.out.println("how much would you like to withdraw:");
			break;
		case deleteAccount:
			System.out.println("enter account id:");
			break;
		default:
			System.out.println("unknown command");
			break;
		}
	}
	
	public static  void switchState(states newState)
	{
		state = newState;
	}

public static void	startOptions(String input)
{
	if(input.equals("1"))
	{
		switchState(states.logging);
	}
	else if(input.equals("2"))
	{
		switchState(states.createUser);
	}
	else if(input.equals("3"))
	{
		System.out.println("goodbye");
		bankAppLive = false;
	}
	else{
		System.out.println("invalid command");
	}
}


public static void loggingOptions(String  input, Scanner scanner)
{
	User user = userService.getUser(input);
	
	if(user==null)
	{
		System.out.println("user doesnt exist");
		return;
		
	}
	System.out.println(" enter password:");
	String userPassword = scanner.nextLine();
	if(!userPassword.equals(user.getPassword()))
	{
		System.out.println("invalid password");
		return;
	}
	System.out.println("you are logged in");
	 isLoggedIn = true;
	 currentUser = user;
	 switchState(states.user);
	 return;
	 
	
}

	
public static void createUserOptions(String input, Scanner scanner)
{
	User user = userService.getUser(input);
	if(user!=null)
	{
		System.out.println("user name taken");
		return;	
	}
	System.out.println(" enter password:");
	String userPassword = scanner.nextLine();
	Integer userId = userService.createUser(input, userPassword);
	if(userId==null)
	{
		System.out.println("fail to create usesr");
		return;
	}
	System.out.println("user created");
	switchState(states.logging);
	return;
	
}
public static void userOptions(String input)
{
	switch(input)
	{
	
	case "1":
		System.out.println(accountService.getAccount(((Integer)Integer.parseInt(input))));	
		break;
	case "2":
		accountService.createAccount(currentUser.getUserID());
		break;
	case "3":
		switchState(states.deposit);
		break;
	case "4":
		switchState(states.withdraw);
		break;
	case "5":
		switchState(states.deleteAccount);
		break;
	case "6":
		switchState(states.start);
		isLoggedIn=false;
		break;
		
		
	}
}
public static void	depositOptions(String input)
{
	System.out.println("enter your account number");
	
	Integer accountNum = (Integer)Integer.parseInt(reader.nextLine());
	accountService.callDeposit(((Integer)Integer.parseInt(input)), accountNum);
	System.out.println(" you have depositied "+ input + "into your account");
	
}
public static void	withdrawOptions(String input)
{
	System.out.println("enter your account number");
	
	Integer accountNum = (Integer)Integer.parseInt(reader.nextLine());
	accountService.callWithdraw(((Integer)Integer.parseInt(input)), accountNum);
	System.out.println(" you have withdrew "+ input + "from your account");
	
}
public static void	deleteAccountOptions(String input)
{
	accountService.deleteAccount((Integer)Integer.parseInt(input));
}
	
//	public static void testDeposit() throws SQLException
//	{
//		AccountsServices accountService = AccountsServices.getAccountService();
//		//List<Account> listOfAccounts = new ArrayList();
//		//Optional<List<Account>>optionalAccount = accountService.getAccount();
//		int amount;
//		int id = 25;
//		amount = 123;
//		AccountsOracle account = new AccountsOracle();
//		//id = 2;
//		account.depositToAccount(123, 25, amount);
//		System.out.println(accountService);
//		/*if(optionalAccount.isPresent())
//		{
//			account.depositToAccount(123, 25);
//			System.out.println(accountService);
//		}*/
//		
//		
//	}
//	public static void testGetAllAccounts()throws UsernameAlreadyExistsException, SQLException
//	{
//		AccountsServices accountService = AccountsServices.getAccountService();
//		List<Account> listOfAccounts = new ArrayList();
//		Optional<List<Account>>optionalAccount = accountService.getAccount();
//		if(optionalAccount.isPresent())
//		{
//			for(Account account: optionalAccount.get())
//			{
//				listOfAccounts.add(account);
//				System.out.println(account.getAccountId()+" "+account.getUserID()+" "+account.getBalance());
//				System.out.println(account);
//			}
//		}
//	}
//	
//	
//	
//	public static void testCreateUser() {
//		UsersService userService = UsersService.getService();
//		List<User> listOfUsers = new ArrayList<>();
//		try {
//			Optional<User> optionalUser = userService.createUser("jason", "smith");
//			if (optionalUser.isPresent()) {		User myUser = optionalUser.get();
//				System.out.println(myUser.getUserID());
//				System.out.println(myUser.getUsername());
//			System.out.println(myUser.getPassword());
//				System.out.println(myUser.getSuperUser());
//			}
//		} catch (UsernameAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
//		}
}


