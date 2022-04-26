package User;

public class Credentials {
	private String username;
	private String password;
	private static final int MIN_SIZE = 4;
	private static final int MAX_SIZE = 8;

	public Credentials(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	private void setUsername(String username) {
		this.username = isUsernameValid(username) ? username : "default";
	}

	private void setPassword(String password) {
		this.password = isPasswordValid(password) ? password : "default";
	}

	protected boolean isUsernameEqualls(String username) {
		return this.username.equals(username);
	}

	protected boolean isPasswordEqualls(String password) {
		return this.password.equals(password);
	}

	/**
	 * Checks if the user name contains letters and/or digits only.
	 * @param username string to check.
	 * @return true if valid.
	 */
	public static boolean isUsernameValid(String username) {
		for (int i = 0; i < username.length(); i++) {
			if (!Character.isLetterOrDigit(username.charAt(i))) {
				System.out.println("Username must be letters and digits only.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the password is of required length and contains letters and digits only.
	 * @param password string to check.
	 * @return true if valid.
	 */
	public static boolean isPasswordValid(String password) {
		int length = password.length();

		if (length < MIN_SIZE || length > MAX_SIZE) {
			System.out.printf("Password must be of size %d-%d.\n", MIN_SIZE, MAX_SIZE);
			return false;
		}

		boolean hasLetter = false, hasDigit = false;

		for (int i = 0; i < length; i++) {
			if (Character.isLetter(password.charAt(i))) {
				hasLetter = true;
			} else if (Character.isDigit(password.charAt(i))) {
				hasDigit = true;
			} else {
				System.out.println("Password must be letters and digits only.");
				return false;
			}
		}

		if (!hasLetter || !hasDigit) {
			System.out.println("Password must contain letters and digits.");
			return false;
		}

		return true;
	}
}
