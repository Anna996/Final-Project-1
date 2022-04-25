package Runner;

import java.time.LocalDate;
import java.util.Scanner;

import AppManager.AppManager;
import DataBase.DB;
import Menu.Menu;
import User.AccountOwner;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

public class Runner {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BankManager manager = createBankManager();
		DB.addUser(manager);
		AppManager appManager = new AppManager(manager);
		int input = 1;

		addDefaultUserToDB();
		while (input != 0) {
			Menu.printWelcomeMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();
			if (input == 1) {
				appManager.openAccount();
			} else if (input == 2) {
				appManager.login();
			}

			Menu.printNewLine();
		}

		System.out.println("The system is off.");
		scanner.close();
		appManager.closeScanner();
	}

	private static BankManager createBankManager() {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555555");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("manager", "manager123");

		return new BankManager(phoneNumber, "Avi", "Levi", birthDate, credentials);
	}
	
	private static void addDefaultUserToDB() {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555554");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("bbb", "bbb123");
		AccountOwner user = new AccountOwner(phoneNumber, "Noa", "Levi", birthDate, credentials, 8000);
		
		DB.addUser(user);
	}
}
