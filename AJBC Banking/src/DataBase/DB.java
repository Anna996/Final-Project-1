package DataBase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import User.AccountOwner;
import User.PhoneNumber;

/**
 * Holds the database of the users that were created in the system.
 * 
 * @author Anna Aba
 *
 */
public class DB {
	public static List<AccountOwner> users;

	static {
		users = new LinkedList<>();
	}

	/**
	 * Search for the user that has this phone number.
	 * 
	 * @param phoneNumber the required phone number.
	 * @return the user that has this phone number, if doesn't exist, return null;
	 */
	public static AccountOwner getUser(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return null;

		Iterator<AccountOwner> iterator = users.iterator();
		while (iterator.hasNext()) {
			AccountOwner user = iterator.next();
			if (phoneNumber.equals(user.getPhoneNumber())) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Search for the user that has this user name.
	 * 
	 * @param username the required user name.
	 * @return the user that has this user name, if doesn't exist, return null;
	 */
	public static AccountOwner getUser(String username) {
		if(username == null) return null;
		Iterator<AccountOwner> iterator = users.iterator();
		
		while (iterator.hasNext()) {
			AccountOwner user = iterator.next();
			if (user.isUsernameEqualls(username)) {
				return user;
			}
		}

		return null;
	}

	/**
	 * Add the user to the static database.
	 * 
	 * @param user that needs to be added.
	 */
	public static void addUser(AccountOwner user) {
		users.add(user);
	}

	/**
	 * Checks if the user exists in the database.
	 * 
	 * @param username the user's user name.
	 * @return true if exists, otherwise false;
	 */
	public static boolean isUsernameExists(String username) {
		return getUser(username) != null;
	}
}
