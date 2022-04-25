package Account;

import java.time.LocalDateTime;

import ActivityData.ActivityData;
import ActivityData.ActivityName;
import DataBase.DB;

public class Account {
	private final long ACCOUNT_ID;
	private static long accountCounter = 0;
	private double balance;
	private float interestRate;
	private float operationFee;
	private AccountProperties accountProperties;
	private ActivityData[] activities;
	private int idx;

	public Account(AccountProperties accountProperties, float interestRate, float operationFee) {
		ACCOUNT_ID = ++accountCounter;
		setAccountProperties(accountProperties);
		setInterestRate(interestRate);
		setOperationFee(operationFee);
		setBalance(0);
		activities = new ActivityData[DB.SIZE];
		idx = 0;
	}

	private void setBalance(double balance) {
		this.balance = balance;
	}

	private void setAccountProperties(AccountProperties accountProperties) {
		this.accountProperties = accountProperties;
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

	private void addActivityData(ActivityData activityData) {
		if (idx >= activities.length) {
			System.out.println("Overflow! class: Account. cant add another activityData.");
			return;
		}
		activities[idx++] = activityData;
	}

	private void handleNewActivityData(ActivityName activityName, LocalDateTime timeStamp, String info,
			double balanceChange) {
		ActivityData activityData = new ActivityData(activityName, timeStamp, info, balanceChange);
		addActivityData(activityData);
	}

	public void depositCash(int amount) {
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.DEPOSIT_CASH, LocalDateTime.now(), "", amount);
	}
}
