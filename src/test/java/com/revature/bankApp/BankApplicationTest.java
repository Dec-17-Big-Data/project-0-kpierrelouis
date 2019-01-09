package com.revature.bankApp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.bankApp.*;

public class BankApplicationTest {

	@Test
	public void createValidUsernameCharecterLength() {
		String username = new String();
		assertTrue(username.length()<=15);
	}

	@Test
	public void loginIsText() {
		String username = new String();
		assertTrue(username.equals(new String()));
	}
}
