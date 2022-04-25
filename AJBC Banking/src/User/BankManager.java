package User;

import java.time.LocalDate;

import DataBase.DB;

public class BankManager extends AccountOwner {
	private AccountOwner[] usersToApprove;
	private int idx;

	public BankManager(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials, double monthlyIncome) {
		super(phoneNumber, firstName, lastName, birthDate, credentials, monthlyIncome);
		this.usersToApprove = new AccountOwner[DB.SIZE];
		this.idx = 0;
	}

	public void addUserToApprove(AccountOwner user) {
		if (idx >= usersToApprove.length) {
			System.out.println("Overflow !!! class: BankManager , cant add another user to approve.");
			return;
		}

		usersToApprove[idx++] = user;
	}

	public void setAndApproveAccounts() {
		for (int i = 0; i < idx; i++) {
			setAndApproveAccount(usersToApprove[i]);
			usersToApprove[i] = null;
		}

		this.idx = 0;
	}

	// TODO setAndApproveAccount(...)
	private void setAndApproveAccount(AccountOwner user) {

	}
}
