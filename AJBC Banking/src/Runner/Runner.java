package Runner;

import java.time.LocalDate;
import java.util.Scanner;

import AppManager.AppManager;
import Menu.Menu;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

public class Runner {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BankManager manager = createBankManager();
		AppManager appManager = new AppManager(manager);
		int input = 1;

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

		scanner.close();
		appManager.closeScanner();
	}

	private static BankManager createBankManager() {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555555");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("manager", "manager123");

		return new BankManager(phoneNumber, "Avi", "Levi", birthDate, credentials);
	}
}
