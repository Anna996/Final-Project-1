package AppManager;

import java.time.LocalDate;
import java.util.Scanner;

import DataBase.DB;
import User.AccountOwner;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

public class AppManager {
	private Scanner scanner;
	private BankManager manager;

	public AppManager(BankManager manager) {
		scanner = new Scanner(System.in);
		setManager(manager);
	}
	
	private void setManager(BankManager manager) {
		this.manager = manager;
	}

	public void openAccount() {
		System.out.println("Openning account...");
		System.out.print("Enter your phone number: ");
		String numberStr = scanner.next();
		PhoneNumber phoneNumber = PhoneNumber.getPhoneNumber(numberStr);
		AccountOwner owner = DB.getUser(phoneNumber);
		if (owner == null) {
			createAccountOwner(phoneNumber);
			System.out.println("Your application is waiting for a manager approval.\nPlease come back later. Thank you!");
		} else {
			login();
		}
	}

	private void createAccountOwner(PhoneNumber phoneNumber) {
		System.out.print("Enter your first name: ");
		String fisrtName = scanner.next();
		System.out.print("Enter your last name: ");
		String lastName = scanner.next();
		System.out.println("Enter your date of birth: ");
		System.out.print("day: ");
		int day = scanner.nextInt();
		System.out.print("month: ");
		int month = scanner.nextInt();
		System.out.print("year: ");
		int year = scanner.nextInt();
		System.out.print("Enter your monthly income: ");
		double monthlyIncome = scanner.nextDouble();
		System.out.print("Enter username: ");
		String username = scanner.next();
		System.out.print("Enter password: ");
		String password = scanner.next();
		Credentials credentials = new Credentials(username, password);

		AccountOwner accountOwner = new AccountOwner(phoneNumber, fisrtName, lastName, LocalDate.of(year, month, day),
				credentials, monthlyIncome);
		
		manager.addUserToApprove(accountOwner);
		DB.addUser(accountOwner);
	}

	public void login() {
		System.out.println("Login...");
	}

	public void closeScanner() {
		this.scanner.close();
	}
}
