package com.revature.bankApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
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
	static enum  states  {start,logging,createUser,user,deposit,withdraw,deleteAccount,admin,deleteUser,updateUser,adminLogin};
	static states state = states.start;
	static Boolean isAdmin= false;
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
					if(isAdmin==true)
					{
						switchState(states.admin);
					}else {
						switchState(states.start);
					}
					
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
			case admin:
				adminOpition(input);
				break;
			case deleteUser:
				if(input.equals("return"))
				{
					switchState(states.admin);
				}else {
					 deleteUser(input);
				}
				
				break;
			case updateUser:
				if(input.equals("return"))
				{
					switchState(states.admin);
				}else {
					updateUserOptions(input);
				}

				
				break;
			case adminLogin:
				if(input.equals("return"))
				{
					switchState(states.start);
				}else {
					adminLoginOptions(input);
				}
				
				break;
				
				
			default:
				break;
			}
		}
		
		
		
		

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
			System.out.println("4 admin login");
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
		case admin:
			System.out.println("admin options");
			System.out.println("1 view users");
			System.out.println("2 create user");
			System.out.println("3 delete user");
			System.out.println("4 update user");
			System.out.println("5 logout");
			break;
		case deleteUser:
			System.out.println(" enter username for deletion");
			break;
		case updateUser:
			System.out.println("enter current username");
			break;
		case adminLogin:
			System.out.println("enter admin username");
			break;
		default:
			System.out.println("unknown command");
			break;
		}
	}
	
	public static  void switchState(states newState)
	{
		state = newState;
		System.out.println("\n\n\n\n\n");
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
	else if(input.equals("4"))
	{
		switchState(states.adminLogin);
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
	
	return;
	
}
public static void userOptions(String input)
{
	switch(input)
	{
	
	case "1":
		System.out.println(accountService.getAllAccounts(currentUser.getUserID()));	
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
	Account account = accountService.getAccount(((Integer)Integer.parseInt(input)));
	
	if(account==null)
	{
		System.out.println("could not find that account");
		return;
	}
	if(account.getBalance()>0)
	{
		System.out.println("can't remove cause theres money");
		return;
		
	}
	
	if(account.getUserID()!= currentUser.getUserID())
	{
		System.out.println(" account dosnt belong to you");
		return;
		
	}
	
	accountService.deleteAccount((Integer)Integer.parseInt(input));
	
}

	public static void deleteUser(String userName)
	{
		if(userService.callDeleteUser(userName)==false)
		{
			System.out.println("could not delete user");
			return;
		}
		System.out.println("deleted user");
	}
	
	public static void updateUserOptions(String input)
	{
		System.out.println(" enter a new name ");
		String newName = reader.nextLine();
		if (userService.callUpdateUser(input, newName)==false)
		{
			System.out.println("could not update user");
			return;
		}
		System.out.println("user has been updated");
		
	}
	
	public static  void adminLoginOptions(String input)
	{
		InputStream in = null;
		Properties props = new Properties();
		String username = null;
		String password = null;
        try {
        	in = new FileInputStream("C:\\Users\\kevin\\Revature\\Java Workspace\\JDBCBank\\src\\main\\resources\\admin.properties");
			props.load(in);
			  username = props.getProperty("username");
              password = props.getProperty("password");
			} 
       catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("enter an password");
        String  inputPassword = reader.nextLine();
        
        if(!inputPassword.equals(password) || !input.equals(username))
        {
        	System.out.println("admin could not log in");
        	return;
        }
        System.out.println("admin logged in");
        isAdmin = true;
        switchState(states.admin);
        
	}
	
	
	public static void adminOpition(String input)
	{
		if(input.equals("return"))
		{
			switchState(states.start);
		}
		switch(input)
		{
		case "1":
			List<User>userList =  userService.getAllUsers();
			if(userList.isEmpty() || userList==null)
			{
				System.out.println("no users found");
				return;
			}
			for(int i=0; i<userList.size();i++)
			{
				System.out.println(userList.get(i).toString());
			}
			System.out.println("complete list of users");
			break;
		case "2":
			switchState(states.createUser);;
		
			
			break;
		case "3":
			switchState(states.deleteUser);
			break;
		case "4":
			switchState(states.updateUser);
			break;
		case "5":
			isLoggedIn = false;
			isAdmin = false;
			switchState(states.start);
			
			break;
		 default:
			break;
		}
	}
	

}


