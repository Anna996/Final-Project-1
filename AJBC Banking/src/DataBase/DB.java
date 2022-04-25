package DataBase;

import Account.AccountProperties;
import User.AccountOwner;
import User.PhoneNumber;

public class DB {
	public static final int SIZE = 100;
	public static AccountOwner[] owners;
	private static int idx;

	static {
		owners = new AccountOwner[SIZE];
		idx = 0;
	}

	public static AccountOwner getAccountOwner(PhoneNumber phoneNumber) {
		for (int i = 0; i < idx; i++) {
			if (phoneNumber.equals(owners[i].getPhoneNumber())) {
				return owners[i];
			}
		}
		return null;
	}
}
