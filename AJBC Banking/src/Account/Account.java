package Account;

import java.time.LocalDateTime;

import ActivityData.ActivityData;
import ActivityData.ActivityName;
import DataBase.DB;
import User.AccountOwner;

public class Account {
	private final long ACCOUNT_ID;
	private static long accountCounter = 0;
	private double balance;
	private float interestRate;
	private float operationFee;
	private AccountProperties accountProperties;
	private ActivityData[] activities;
	private int idx;
	private final int MAX_TO_TRANSFER = 2000;
	private final int MAX_FOR_BILL = 5000;

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

	private void handleNewActivityData(ActivityName activityName, String info, double balanceChange) {
		ActivityData activityData = new ActivityData(activityName, LocalDateTime.now(), info, balanceChange);
		addActivityData(activityData);
	}

	public void depositCash(int amount) {
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.DEPOSIT_CASH, "none", amount);
	}

	// TODO getActivitiesDataFrom(timestamp)
	public ActivityData[] getActivitiesDataFrom(LocalDateTime timestamp) {
		return new ActivityData[0];
	}

	public boolean withdrawalCash(int amount) {
		if (amount <= accountProperties.maxWithdrawalAmount) {
			setBalance(balance - amount);
			handleNewActivityData(ActivityName.WITHDRAWAL, "none", -amount);
			return true;
		} else {
			System.out.println("Cannot do the oparation. The amount exceeds the daily maximum.");
			return false;
		}
	}

	public boolean transfer(int amount) {
		if (amount <= MAX_TO_TRANSFER) {
			setBalance(balance - amount);
			handleNewActivityData(ActivityName.MAKE_PAYMENTTRANSFER, "transfer to another user", -amount);
			return true;
		} else {
			System.out.printf("Cannot do the oparation. The maximum to transfer is %d.\n", MAX_TO_TRANSFER);
			return false;
		}
	}

	public void transferredToMe(int amount) {
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.MAKE_PAYMENTTRANSFER, "transferred from other user", amount);
	}

	public boolean payBill(int amount, String payee) {
		if (amount <= MAX_FOR_BILL) {
			setBalance(balance - amount);
			handleNewActivityData(ActivityName.PAY_BILL, "Payee: " + payee, -amount);
			return true;
		} else {
			System.out.printf("Cannot do the oparation. The maximum for bill payment is %d.\n", MAX_FOR_BILL);
			return false;
		}
	}

	public boolean isLoanAmountAcceptable(int amount) {
		return amount <= accountProperties.maxLoanAmmount;
	}
	
	public void getLoan(int amount, int numOfPayments) {
		// TODO loan object
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.GET_LOAN, "numOfPayments: " + numOfPayments, amount);
	}
}
