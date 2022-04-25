package User;

import java.time.LocalDate;

import Account.Account;

public class AccountOwner extends Person {
	private Credentials credentials;
	private Account account = null;
	private double monthlyIncome;

	public AccountOwner(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials,double monthlyIncome) {
		super(phoneNumber, firstName, lastName, birthDate);
		setCredentials(credentials);
		setMonthlyIncome(monthlyIncome);
	}

	private void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	private void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	
	

//	private void setAccount(Account account) {
//		this.account = account;
//	}
}
