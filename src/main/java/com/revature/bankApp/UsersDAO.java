package com.revature.bankApp;

import java.util.List;
import java.util.Optional;

public interface UsersDAO {

	 Optional <List<UsersModel>> getAllUsers();
	 Optional <UsersModel> getUserFirstName();
	 Optional <UsersModel>  getUserLastName();
}
