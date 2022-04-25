package User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

import Account.Account;
import ActivityData.ActivityData;
import Menu.Menu;
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

		if (input == code) {
			System.out.print("Enter amount of cash: ");
			int amountOfCash = StaticScanner.scanner.nextInt();
			System.out.println("Dropping into the ATM box....");
			account.depositCash(amountOfCash);
			System.out.printf("Successful deposit of %s was made.\n", amountOfCash);
		} else {
			System.out.println("Your code incorrect.");
		}
	}

	private int generateAuthenticationCode() {
		Random random = new Random();
		int code = 0;

		for (int i = 0; i < 4; i++) {
			code *= 10;
			code += 1 + random.nextInt(9);
		}

		return code;
	}

	public void makeWithdrawal() {
		System.out.print("Enter amount to withdrawal: ");
		int amount = StaticScanner.scanner.nextInt();
		
		if(account.withdrawalCash(amount)) {
			System.out.println("The funds got out from the ATM box....");
			System.out.printf("Successful withdrawal of %s was made.\n", amount);
		}
	}

	// TODO use cases:
	public void transferFunds() {

	}

	public void payBill() {

	}

	public void askForLoan() {

	}

	public void getActivityReport() {
		Scanner scanner = StaticScanner.scanner;

		System.out.println("Enter start date:");
		System.out.print("day: ");
		int day = scanner.nextInt();
		System.out.print("month: ");
		int month = scanner.nextInt();
		System.out.print("year: ");
		int year = scanner.nextInt();

		getActivityReportData(LocalDateTime.of(year, month, day, 0, 0));

	}

	// TODO getActivityReportData
	protected void getActivityReportData(LocalDateTime timestamp) {
		ActivityData[] activities = account.getActivitiesDataFrom(timestamp);
		
		System.out.println("Activities:");
		System.out.println("===========");
		for(ActivityData data : activities) {
			System.out.println(data);
		}
		
		Menu.printNewLine();
		checkBalance();
		
		// TODO Loan summary
		
	}
}
