package AppManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import Account.Account;
import Account.AccountProperties;
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

	public AppManager() {
		scanner = StaticScanner.scanner;
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
				monthlyIncome, manager);

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
			currentUser.setAccountBankManager();
			handleMenu();
		} else {
			Menu.printApplicationWaitting();
		}

		logout();
	}

	private void handleMenu() {
		int input;

		do {
			Menu.printNewLine();
			currentUser.showMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();
			currentUser.chooseMenuItem(input);
		}while (input != 0);
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

	public void startSystem() {
		setManager(createBankManager());
		DB.addUser(manager);
		addDefaultUserToDB(manager);

		int input = 1;
		while (input != 0) {
			Menu.printWelcomeMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();
			if (input == 1) {
				openAccount();
			} else if (input == 2) {
				login();
			}

			Menu.printNewLine();
		}

		System.out.println("The system is off.");
		scanner.close();
	}

	private BankManager createBankManager() {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555555");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("m", "mm11");

		return new BankManager(phoneNumber, "Avi", "Levi", birthDate, credentials);
	}

	private void addDefaultUserToDB(BankManager manager) {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555554");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("n", "nn11");
		AccountOwner user = new AccountOwner(phoneNumber, "Noa", "Levi", birthDate, credentials, 8000, manager);
		Account account = new Account(AccountProperties.BRONZE, 4.5f, 5);

		user.setAccount(account);
		DB.addUser(user);
	}
}
