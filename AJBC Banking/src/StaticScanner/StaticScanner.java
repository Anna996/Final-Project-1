package StaticScanner;

import java.util.Scanner;

public class StaticScanner {
	public static Scanner scanner;
	
	static {
		scanner = new Scanner(System.in);
	}
}
