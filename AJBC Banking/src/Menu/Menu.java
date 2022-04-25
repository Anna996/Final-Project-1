package Menu;

public class Menu {

	public static void printWelcomeMenu() {
		System.out.println("Welcome to our bank !");
		System.out.println("=====================");
		System.out.println("1. Open bank account");
		System.out.println("2. Log in");
	}

	public static void printNewLine() {
		System.out.println("");
	}

	public static void printEnterYourChoise() {
		System.out.print("Enter your choise: ");
	}

	public static void printApplicationWaitting() {
		System.out.println("Your application is waiting for a manager approval.\nPlease come back later. Thank you!");
	}

	public static void printActivitiesMenu() {
		System.out.println("Available operations");
		System.out.println("====================");
		System.out.println("1. Check balance");
		System.out.println("2. Deposit cash");
		System.out.println("3. make a withdrawal");
		System.out.println("4. Transfer funds");
		System.out.println("5. Pay a bill");
		System.out.println("6. Ask for loan");
		System.out.println("7. Activity report");
		System.out.println("0. Log out");
	}
}
