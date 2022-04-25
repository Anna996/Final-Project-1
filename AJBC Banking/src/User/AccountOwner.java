package User;

import java.time.LocalDate;

import Account.Account;

public class AccountOwner extends Person {
	private Credentials credentials;
	private Account account = null;
	private double monthlyIncome;
//	private BankManager manager;

	public AccountOwner(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials, double monthlyIncome) {
		super(phoneNumber, firstName, lastName, birthDate);
		setCredentials(credentials);
		setMonthlyIncome(monthlyIncome);
//		setManager(manager);
	}

	private void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	private void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

//	private void setManager(BankManager manager) {
//		this.manager = manager;
//	}

//	private void setAccount(Account account) {
//		this.account = account;
//	}

	public boolean isUsernameEqualls(String username) {
		return this.credentials.isUsernameEqualls(username);
	}

	public boolean isPasswordEqualls(String password) {
		return this.credentials.isPasswordEqualls(password);
	}

	public boolean hasAccount() {
		return account != null;
	}
}
