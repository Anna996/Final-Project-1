package User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

import Account.Account;
import Account.Payee;
import ActivityData.ActivityData;
import DataBase.DB;
import Menu.Menu;
import StaticScanner.StaticScanner;

public class AccountOwner extends Person {
	private Credentials credentials;
	private Account account = null;
	private double monthlyIncome;
	private BankManager manager;
	private final int MAX_LOAN_PAYMENTS = 60;

	public AccountOwner(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials, double monthlyIncome, BankManager manager) {
		super(phoneNumber, firstName, lastName, birthDate);
		setCredentials(credentials);
		setMonthlyIncome(monthlyIncome);
		setBankManager(manager);
		if (manager != null)
			this.manager.addUserToApprove(this);
	}

	private void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	private void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private void setBankManager(BankManager bankManager) {
		this.manager = bankManager;
	}

	public void setAccountBankManager() {
		if(account.getManager() == null) {
			account.setManager(manager);
		}
	}

	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	protected Account getAccount() {
		return account;
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
		System.out.printf("Balance: %.2f\n", account.getBalance());
	}

	/**
	 * Asks the user for code authentication and the amount of money to deposit.
	 * Than makes the activity.
	 */
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

	/**
	 * Asks the user for amount to withdrawal. Than makes the activity.
	 */
	public void makeWithdrawal() {
		System.out.print("Enter amount to withdrawal: ");
		int amount = StaticScanner.scanner.nextInt();

		if (account.withdrawalCash(amount)) {
			System.out.println("The funds got out from the ATM box....");
			System.out.printf("Successful withdrawal of %d was made.\n", amount);
		}
	}

	/**
	 * Asks the user for the receiver's phone number, and the amount to transfer.
	 * Than transfer the funds.
	 */
	public void transferFunds() {
		System.out.print("Enter phone number of the receiver: ");
		String numberStr = StaticScanner.scanner.next();
		PhoneNumber phoneNumber = PhoneNumber.getPhoneNumber(numberStr);
		AccountOwner receiver = DB.getUser(phoneNumber);

		if (receiver == null) {
			System.out.println("The receiver was not found.");
		} else {
			System.out.print("Enter amount to transfer: ");
			int amount = StaticScanner.scanner.nextInt();
			if (account.transfer(amount)) {
				receiver.account.transferredToMe(amount);
				System.out.printf("Successful transfer of %d was made.\n", amount);
			}
		}
	}

	/**
	 * Asks the user to choose the company to pay for, and the amount of the bill.
	 * Than makes the activity.
	 */
	public void payBill() {
		System.out.println("Choose the payee: ");
		for (Payee payee : Payee.values()) {
			System.out.println(Payee.getId(payee) + ". " + payee);
		}

		Menu.printEnterYourChoise();
		int payee = StaticScanner.scanner.nextInt();
		System.out.print("Enter the bill amount: ");
		int amount = StaticScanner.scanner.nextInt();
		if (account.payBill(amount, Payee.getPayee(payee).toString())) {
			System.out.printf("Successful bill paying of %d was made.\n", amount);
		}
	}

	/**
	 * Asks the user for the loan amount, and the number of return payments. Than
	 * creates the loan.
	 */
	public void askForLoan() {
		System.out.print("Enter loan amount: ");
		int loanAmount = StaticScanner.scanner.nextInt();

		if (account.isLoanAmountAcceptable(loanAmount)) {
			System.out.print("Enter the number of monthly payments: ");
			int numOfMonthlyPayments = StaticScanner.scanner.nextInt();
			if (numOfMonthlyPayments <= MAX_LOAN_PAYMENTS) {
				account.getLoan(loanAmount, numOfMonthlyPayments);
				System.out.printf("Successful received loan amount of %d.\n", loanAmount);
			} else {
				System.out.println("Cannot do the oparation. The amount exceeds the maximum payments number.");
			}
		} else {
			System.out.println("Cannot do the oparation. The amount exceeds the maximum loan amount.");
		}
	}

	/**
	 * Asks the user for date start. Than shows the report.
	 */
	public void getActivityReport() {
		Scanner scanner = StaticScanner.scanner;

		System.out.println("Enter start date:");
		System.out.print("day: ");
		int day = scanner.nextInt();
		System.out.print("month: ");
		int month = scanner.nextInt();
		System.out.print("year: ");
		int year = scanner.nextInt();

		Menu.printNewLine();
		System.out.println("Activity Report");
		System.out.println("===============");
		getActivityReportData(LocalDateTime.of(year, month, day, 0, 0));
	}

	// Regular user report that shows the activities that were made from that date,
	// and also current balance and summary loan if exists.
	protected void getActivityReportData(LocalDateTime timestamp) {
		ActivityData[] activities = account.getActivitiesDataFrom(timestamp);

		for (ActivityData data : activities) {
			System.out.println(data);
		}

		Menu.printNewLine();
		checkBalance();

		if (account.getLoan() != null) {
			Menu.printNewLine();
			account.getLoan().printSummary();
		}
	}
}
