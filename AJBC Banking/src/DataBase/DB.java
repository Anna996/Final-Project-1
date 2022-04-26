package DataBase;

import User.AccountOwner;
import User.PhoneNumber;

public class DB {
	public static final int SIZE = 100;
	public static AccountOwner[] users;
	private static int idx;

	static {
		users = new AccountOwner[SIZE];
		idx = 0;
	}

	public static AccountOwner getUser(PhoneNumber phoneNumber) {
		if (phoneNumber == null) return null; 
		for (int i = 0; i < idx; i++) {
			if (phoneNumber.equals(users[i].getPhoneNumber())) {
				return users[i];
			}
		}
		return null;
	}

	public static AccountOwner getUser(String username) {
		for (int i = 0; i < idx; i++) {
			if (users[i].isUsernameEqualls(username)) {
				return users[i];
			}
		}
		return null;
	}

	public static void addUser(AccountOwner user) {
		if (idx >= users.length) {
			System.out.println("Overflow !!! class: DB , cant add another user to database.");
			return;
		}

		users[idx++] = user;
	}
}
