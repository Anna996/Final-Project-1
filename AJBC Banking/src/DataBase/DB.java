package DataBase;

import User.AccountOwner;
import User.PhoneNumber;

/**
 * Holds the database of the users that were created in the system.
 * @author Anna Aba
 *
 */
public class DB {
	public static final int SIZE = 100;
	public static AccountOwner[] users;
	private static int idx;

	static {
		users = new AccountOwner[SIZE];
		idx = 0;
	}

	/**
	 * Search for the user that has this phone number.
	 * @param phoneNumber the required phone number.
	 * @return the user that has this phone number, if doesn't exist, return null;
	 */
	public static AccountOwner getUser(PhoneNumber phoneNumber) {
		if (phoneNumber == null) return null; 
		for (int i = 0; i < idx; i++) {
			if (phoneNumber.equals(users[i].getPhoneNumber())) {
				return users[i];
			}
		}
		return null;
	}

	/**
	 * Search for the user that has this user name.
	 * @param username the required user name.
	 * @return the user that has this user name, if doesn't exist, return null;
	 */
	public static AccountOwner getUser(String username) {
		for (int i = 0; i < idx; i++) {
			if (users[i].isUsernameEqualls(username)) {
				return users[i];
			}
		}
		return null;
	}

	/**
	 * Add the user to the static database.
	 * @param user that needs to be added.
	 */
	public static void addUser(AccountOwner user) {
		if (idx >= users.length) {
			System.out.println("Overflow !!! class: DB , cant add another user to database.");
			return;
		}

		users[idx++] = user;
	}
	
	/**
	 * Checks if the user exists in the database.
	 * @param username the user's user name.
	 * @return true if exists, otherwise false;
	 */
	public static boolean isUsernameExists(String username) {
		return getUser(username) != null;
	}
}
