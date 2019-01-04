package com.revature.bankApp;

import java.util.Optional;

public interface ServicesDAO {

	Optional <ServicesModel> getDepositAmout();
	Optional <ServicesModel> getWithdrawAmount();
}
