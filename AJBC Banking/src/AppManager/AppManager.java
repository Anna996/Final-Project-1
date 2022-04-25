package AppManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import DataBase.DB;
import Menu.Menu;
import User.AccountOwner;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

public class AppManager {
	private Scanner scanner;
	private BankManager manager;
	private AccountOwner currentUser;

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
			Menu.printApplicationWaitting();
		} else {
			System.out.println("You already have an account.");
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
		String username, password;
		AccountOwner userFromDB = null;
		int attempts = 3;

		System.out.println("Login...");

		// input and validation of user-name field
		while (attempts > 0) {
			System.out.print("Enter username: ");
			username = scanner.next();
			userFromDB = DB.getUser(username);
			if (userFromDB != null) {
				break;
			}
			System.out.println("Username incorrect.");
			attempts--;
		}

		// input and validation of password field
		while (attempts > 0) {
			System.out.print("Enter password: ");
			password = scanner.next();
			if (userFromDB.isPasswordEqualls(password)) {
				currentUser = userFromDB;
				userIsInTheSystem();
				return;
			} else {
				System.out.println("Password incorrect.");
				attempts--;
			}
		}

		lockUser();
	}

	private void userIsInTheSystem() {
		Menu.printNewLine();
		System.out.printf("Hello %s !\n", currentUser.getFullName());
		System.out.println("you are logged in.");
		if (currentUser.hasAccount()) {
			showRegularMenu();
		} else {
			Menu.printApplicationWaitting();
		}
		
		logout();
	}

	private void showRegularMenu() {
		int input = 1;

		while (input != 0) {
			Menu.printNewLine();
			Menu.printActivitiesMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();

			switch (input) {
			case 1:
				//TODO use case 1
				break;
			}
		}
	}

	private void lockUser() {
		LocalTime realseTime = LocalTime.now().plusMinutes(30);

		Menu.printNewLine();
		System.out.println("your account is being locked...");
		System.out.printf("It will be realsed at %d:%d \n", realseTime.getHour(), realseTime.getMinute());
		while (realseTime.isAfter(LocalTime.now())) {
		}
	}

	private void logout() {
		Menu.printNewLine();
		System.out.printf("GoodBye %s !\n", currentUser.getFullName());
		this.currentUser = null;
	}

	public void closeScanner() {
		this.scanner.close();
	}
}
