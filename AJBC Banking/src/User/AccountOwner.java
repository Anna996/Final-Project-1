package User;

import java.time.LocalDate;
import java.util.Random;

import Account.Account;
import StaticScanner.StaticScanner;

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

	// TODO change setAccount to protected
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public boolean isUsernameEqualls(String username) {
		return this.credentials.isUsernameEqualls(username);
	}

	public boolean isPasswordEqualls(String password) {
		return this.credentials.isPasswordEqualls(password);
	}

	public boolean hasAccount() {
		return account != null;
	}

	public void checkBalance() {
		System.out.println("Balance: " + account.getBalance());
	}

	public void depositCash() {
		int code = generateAuthenticationCode();
		
		System.out.println("You got an authentication code: " + code);
		System.out.print("Please enter your authentication code: ");
		int input = StaticScanner.scanner.nextInt();
		
		if(input == code) {
			System.out.print("Enter amount of cash: ");
			int amountOfCash = StaticScanner.scanner.nextInt();
			System.out.println("Dropping into the ATM box....");
			account.depositCash(amountOfCash);
			System.out.printf("Successful deposit of %s was made.\n",amountOfCash);
		}
		else {
			System.out.println("Your code incorrect.");
		}
	}

	private int generateAuthenticationCode() {
		Random random = new Random();
		int code = 0;

		for (int i = 0; i < 4; i++) {
			code *= 10;
			code += random.nextInt(10);
		}

		return code;
	}
}
