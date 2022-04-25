package Account;

import DataBase.DB;

public class Account {
	private final long ACCOUNT_ID;
	private static long accountCounter = 0;
	private double balance;
	private float interestRate;
	private float operationFee;
	private AccountProperties accountProperties;
	private ActivityData[] activities;
	
	public Account(AccountProperties accountProperties,float interestRate, float operationFee) {
		ACCOUNT_ID = ++accountCounter;
		setAccountProperties(accountProperties);
		setInterestRate(interestRate);
		setOperationFee(operationFee);
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
	
	private void setInterestRate(float interestRate) {
		boolean inRange = accountProperties.isInterestRateInRagne(interestRate);
		this.interestRate = inRange ? interestRate : accountProperties.maxInterestRate;
	}

	private void setOperationFee(float operationFee) {
		boolean inRange = accountProperties.isOperationFeeInRagne(operationFee);
		this.operationFee = inRange ? operationFee : accountProperties.maxOperationFee;
	}

	public double getBalance() {
		return balance;
	}
	
	public void depositCash(int amount) {
		setBalance(balance + amount);
		// TODO track the history
	}
}
