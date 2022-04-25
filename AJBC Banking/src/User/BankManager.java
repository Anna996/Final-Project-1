package User;

import java.time.LocalDate;

import Account.Account;
import Account.AccountProperties;
import DataBase.DB;

public class BankManager extends AccountOwner {
	private AccountOwner[] usersToApprove;
	private int idx;

	public BankManager(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials) {
		super(phoneNumber, firstName, lastName, birthDate, credentials, 0);
		this.usersToApprove = new AccountOwner[DB.SIZE];
		this.idx = 0;
	}

	public void addUserToApprove(AccountOwner user) {
		if (idx >= usersToApprove.length) {
			System.out.println("Overflow !!! class: BankManager , cant add another user to approve.");
			return;
		}

		usersToApprove[idx++] = user;
	}

	public void setAndApproveUsers() {
		for (int i = 0; i < idx; i++) {
			setAndApproveAccount(usersToApprove[i]);
			usersToApprove[i] = null;
		}

		this.idx = 0;
	}

	private void setAndApproveAccount(AccountOwner user) {
		AccountProperties properties;
		float interestRate, operationFee;
		double income = user.getMonthlyIncome();

		if (income < 10000) {
			properties = AccountProperties.BRONZE;
			interestRate = properties.getMinInterestRate();
			operationFee = properties.getMinOperationFee();
		} else if (income < 18000) {
			properties = AccountProperties.SILVER;
			interestRate = properties.getMinInterestRate() + 1;
			operationFee = properties.getMinOperationFee() + 0.5f;
		} else {
			properties = AccountProperties.GOLD;
			interestRate = properties.getMaxInterestRate();
			operationFee = properties.getMaxOperationFee();
		}

		Account account = new Account(properties, interestRate, operationFee);

		user.setAccount(account);
	}

	// TODO what happens when the manager is logged in?
}
