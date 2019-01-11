package com.revature.bankApp;
import com.revature.dao.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;

import com.revature.bankApp.*;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.services.UsersService;

public class BankApplicationTest {

	public UsersOracle oracle = new UsersOracle();
	@Test
	public void createValidUsernameCharecterLength() {
		String username = new String();
		assertTrue(username.length()<=15);
	}

	/*@Test
	public void loginIsText() {
		String username = new String("jim");
		assertTrue(username.equals(new String()));
	}*/
		
//	@Test
//	public void testCreateUser() throws UsernameAlreadyExistsException
//	{
//		Random random = new Random();
//		String username = String.valueOf(random.nextInt()) ;
//		String password = String.valueOf(random.nextInt()) ;
//		
//		UsersService userService = UsersService.getService();
//		Optional<User> optionalUser = userService.createUser(username, password);
//		assertTrue(optionalUser.isPresent());
//	}
   }

