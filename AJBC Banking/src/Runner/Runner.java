package Runner;

import java.util.Scanner;

import AppManager.AppManager;
import Menu.Menu;

public class Runner {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		AppManager appManager = new AppManager();
		int input = 1;
		
		while(input != 0) {
			Menu.printWelcomeMenu();
			Menu.printEnterYourChoise();
			input = scanner.nextInt();
			Menu.printNewLine();
			if(input == 1) {
				appManager.openAccount();
			}
			else if(input == 2){
				appManager.login();
			}
			
			Menu.printNewLine();
		}
		
		scanner.close();
		appManager.closeScanner();
	}
}
