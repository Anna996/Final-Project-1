package Account;

import DataBase.DB;

public class Account {
	private double balance;
	private AccountProperties accountProperties;
	private ActivityData[] activities;
	
	public Account(AccountProperties accountProperties) {
		setAccountProperties(accountProperties);
		setBalance(0);
		setActivities(new ActivityData[DB.SIZE]);
	}

	private void setBalance(double balance) {
		this.balance = balance;
	}

	private void setAccountProperties(AccountProperties accountProperties) {
		this.accountProperties = accountProperties;
	}

	private void setActivities(ActivityData[] activities) {
		this.activities = activities;
	}

	public double getBalance() {
		return balance;
	}
	
	public void depositCash(int amount) {
		setBalance(balance + amount);
	}
}
