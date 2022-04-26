package Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ActivityData.ActivityData;
import ActivityData.ActivityName;
import DataBase.DB;
import User.AccountOwner;
import User.BankManager;

public class Account {
	private final long ACCOUNT_ID;
	private static long accountCounter = 0;
	private double balance;
	private float interestRate;
	private float operationFee;
	private AccountProperties accountProperties;
	private ActivityData[] activities;
	private int idx;
	private Loan loan;
	private BankManager manager;
	private final int MAX_TO_TRANSFER = 2000;
	private final int MAX_FOR_BILL = 5000;

	public Account(AccountProperties accountProperties, float interestRate, float operationFee, BankManager manager) {
		ACCOUNT_ID = ++accountCounter;
		setAccountProperties(accountProperties);
		setInterestRate(interestRate);
		setOperationFee(operationFee);
		setManager(manager);
		setBalance(0);
		activities = new ActivityData[DB.SIZE];
		idx = 0;
		loan = null;
	}

	public void setBalance(double balance) {
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

	private void setManager(BankManager manager) {
		this.manager = manager;
	}

	public double getBalance() {
		return balance;
	}

	public void addActivityData(ActivityData activityData) {
		if (idx >= activities.length) {
			System.out.println("Overflow! class: Account. cant add another activityData.");
			return;
		}
		activities[idx++] = activityData;
	}

	private void handleNewActivityData(ActivityName activityName, String info, double balanceChange) {
		ActivityData activityData;

		setBalance(balance - this.operationFee);
		activityData = new ActivityData(activityName, LocalDateTime.now(), info, balanceChange);
		addActivityData(activityData);
		activityData = new ActivityData(ActivityName.FEE_COLLECTION, LocalDateTime.now(), "Fee operation to bank",
				-this.operationFee);
		addActivityData(activityData);
		manager.makeFeeCollectionPayBill(activityName, this.operationFee);
	}

	public void depositCash(int amount) {
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.DEPOSIT, "none", amount);
	}

	public ActivityData[] getActivitiesDataFrom(LocalDateTime timestamp) {
		LocalDateTime activityTimestamp;
		int i = 0;

		while (i < idx) {
			activityTimestamp = activities[i].getTimeStamp();
			if (isDateAfterOrEqual(timestamp, activityTimestamp)) {
				break;
			}
			i++;
		}

		ActivityData[] resultActivityData = new ActivityData[idx - i];

		for (int j = 0; j < resultActivityData.length; j++, i++) {
			resultActivityData[j] = activities[i];
		}

		return resultActivityData;
	}

	// is date of timestamp2 after or equal date of timestamp1
	private boolean isDateAfterOrEqual(LocalDateTime timestamp1, LocalDateTime timestamp2) {
		LocalDate date1 = LocalDate.of(timestamp1.getYear(), timestamp1.getMonth(), timestamp1.getDayOfMonth());
		LocalDate date2 = LocalDate.of(timestamp2.getYear(), timestamp2.getMonth(), timestamp2.getDayOfMonth());

		return date2.isAfter(date1) || date2.isEqual(date1);
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
			handleNewActivityData(ActivityName.MAKE_PAYMENT_TRANSFER, "transfer to another user", -amount);
			return true;
		} else {
			System.out.printf("Cannot do the oparation. The maximum to transfer is %d.\n", MAX_TO_TRANSFER);
			return false;
		}
	}

	public void transferredToMe(int amount) {
		setBalance(balance + amount);

		ActivityData activityData = new ActivityData(ActivityName.MAKE_PAYMENT_TRANSFER, LocalDateTime.now(),
				"transferred from other user", amount);
		addActivityData(activityData);
	}

	public boolean payBill(int amount, String payee) {
		if (amount <= MAX_FOR_BILL) {
			setBalance(balance - amount);
			handleNewActivityData(ActivityName.PAY_BILL, "Payee: " + payee, -amount);
			if (payee.equals(Payee.AJBC_BANK.toString())) {
				manager.getBillPayment(amount);
			}
			return true;
		} else {
			System.out.printf("Cannot do the oparation. The maximum for bill payment is %d.\n", MAX_FOR_BILL);
			return false;
		}
	}

	public boolean isLoanAmountAcceptable(int amount) {
		return amount <= accountProperties.maxLoanAmmount;
	}

	public Loan getLoan() {
		return this.loan;
	}

	public void getLoan(int amount, int numOfPayments) {
		this.loan = new Loan(amount, numOfPayments);
		System.out.println("The amount of the monthly return: " + loan.getMonthlyPayment());
		setBalance(balance + amount);
		handleNewActivityData(ActivityName.GET_LOAN, "numOfPayments: " + numOfPayments, amount);
		manager.getLoanFromBank(amount);
	}
}
