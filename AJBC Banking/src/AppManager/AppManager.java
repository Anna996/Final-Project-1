package AppManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import DataBase.DB;
import Menu.Menu;
import StaticScanner.StaticScanner;
import User.AccountOwner;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

/**
 * Manages the flow of the different parts of the bank system.
 * 
 * @author Anna Aba
 *
 */
public class AppManager {
	private Scanner scanner;
	private BankManager manager;
	private AccountOwner currentUser;

	public AppManager(BankManager manager) {
		scanner = StaticScanner.scanner;
		setManager(manager);
	}

	private void setManager(BankManager manager) {
		this.manager = manager;
	}

	/**
	 * Asks for phone number - if exists in the database than jumps to login,
	 * otherwise open new user account.
	 */
	public void openAccount() {
		System.out.println("Openning account...");
		System.out.print("Enter your phone number: ");
		String numberStr = scanner.next();
		PhoneNumber phoneNumber = PhoneNumber.getPhoneNumber(numberStr);
		if (phoneNumber == null)
			return;
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
		String fisrtName = askForName("Enter your first name: ");
		String lastName = askForName("Enter your last name: ");
		LocalDate birthDate = askForDateOfBirth();
		double monthlyIncome = askForIncome();
		Credentials credentials = askForCredentials();
		AccountOwner accountOwner = new AccountOwner(phoneNumber, fisrtName, lastName, birthDate, credentials,
				monthlyIncome);

		manager.addUserToApprove(accountOwner);
		DB.addUser(accountOwner);
	}

	private String askForName(String message) {
		String name;

		do {
			System.out.print(message);
			name = scanner.next();
		} while (!isValidName(name));

		return name;
	}

	private boolean isValidName(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i))) {
				System.out.println("Name must be letters only.");
				return false;
			}
		}
		return true;
	}

	private double askForIncome() {
		double monthlyIncome;

		do {
			System.out.print("Enter your monthly income: ");
			monthlyIncome = scanner.nextDouble();
			if (monthlyIncome < 0) {
				System.out.println("Monthly income has to be >= 0");
			}
		} while (monthlyIncome < 0);

		return monthlyIncome;
	}

	private LocalDate askForDateOfBirth() {
		System.out.println("Enter your date of birth: ");
		System.out.print("day: ");
		int day = scanner.nextInt();
		System.out.print("month: ");
		int month = scanner.nextInt();
		System.out.print("year: ");
		int year = scanner.nextInt();

		return LocalDate.of(year, month, day);
	}

	private Credentials askForCredentials() {
		String username, password;
		boolean isExsits;

		do {
			System.out.print("Enter username: ");
			username = scanner.next();
			isExsits = DB.isUsernameExists(username);
			if (isExsits) {
				System.out.println("Username already exists.");
			}
		} while (!Credentials.isUsernameValid(username) || isExsits);

		do {
			System.out.print("Enter password: ");
			password = scanner.next();
		} while (!Credentials.isPasswordValid(password));

		return new Credentials(username, password);
	}

	/**
	 * Asks for user's user-name and password. It gives the user only 3 attempts, if
	 * the user fails to give accurate credentials, then the front-end blocked for
	 * 30 minutes.
	 */
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

	// After the user is Logged in, the system shows him the right menu.
	private void userIsInTheSystem() {
		Menu.printNewLine();
		System.out.printf("Hello %s !\n", currentUser.getFullName());
		System.out.println("you are logged in.");
		if (currentUser.hasAccount()) {
			if (currentUser instanceof BankManager) {
				handleManagerMenu();
			} else {
				handleRegularMenu();
			}
		} else {
			Menu.printApplicationWaitting();
		}

		logout();
	}

	private void handleManagerMenu() {
		BankManager manger = (BankManager) currentUser;
		int input = 1;

		while (input != 0) {
			Menu.printNewLine();
			Menu.printManagerMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();

			switch (input) {
			case 1:
				manger.setAndApproveUsers();
				break;
			case 2:
				manger.getActivityReport();
				break;
			case 3:
				handleRegularMenu();
				return;
			}
		}
	}

	private void handleRegularMenu() {
		int input = 1;

		while (input != 0) {
			Menu.printNewLine();
			Menu.printActivitiesMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();

			switch (input) {
			case 1:
				currentUser.checkBalance();
				break;
			case 2:
				currentUser.depositCash();
				break;
			case 3:
				currentUser.makeWithdrawal();
				break;
			case 4:
				currentUser.transferFunds();
				break;
			case 5:
				currentUser.payBill();
				break;
			case 6:
				currentUser.askForLoan();
				break;
			case 7:
				currentUser.getActivityReport();
				break;
			}
		}
	}

	// Blocks the system with while loop, for 30 minutes.
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
}
