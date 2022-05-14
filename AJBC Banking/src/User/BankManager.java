package User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import Account.Account;
import Account.AccountProperties;
import ActivityData.ActivityData;
import ActivityData.ActivityName;
import DataBase.DB;
import Menu.Menu;

public class BankManager extends AccountOwner {
	private AccountOwner[] usersToApprove;
	private int idx;

	public BankManager(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate,
			Credentials credentials) {
		super(phoneNumber, firstName, lastName, birthDate, credentials, 0, null);
		this.usersToApprove = new AccountOwner[DB.SIZE];
		this.idx = 0;
		setBankAccount();
	}

	public void addUserToApprove(AccountOwner user) {
		if (idx >= usersToApprove.length) {
			System.out.println("Overflow !!! class: BankManager , cant add another user to approve.");
			return;
		}

		usersToApprove[idx++] = user;
	}

	public void setAndApproveUsers() {
		for (int i = 0; i < idx; i++) {
			setAndApproveAccount(usersToApprove[i]);
			usersToApprove[i] = null;
		}

		this.idx = 0;
	}

	private void setAndApproveAccount(AccountOwner user) {
		AccountProperties properties;
		float interestRate, operationFee;
		double income = user.getMonthlyIncome();

		if (income < 10000) {
			properties = AccountProperties.BRONZE;
			interestRate = properties.getMinInterestRate();
			operationFee = properties.getMinOperationFee();
		} else if (income < 18000) {
			properties = AccountProperties.SILVER;
			interestRate = properties.getMinInterestRate() + 1;
			operationFee = properties.getMinOperationFee() + 0.5f;
		} else {
			properties = AccountProperties.GOLD;
			interestRate = properties.getMaxInterestRate();
			operationFee = properties.getMaxOperationFee();
		}

		Account account = new Account(properties, interestRate, operationFee);

		user.setAccount(account);
	}

	private void setBankAccount() {
		Account account = new Account(AccountProperties.TITANIUM, 0, 0);
		setAccount(account);
	}
	
	// Shows manager-report of the change in balance of the bank account from the date of the time-stamp until now, and also shows the balance.
	@Override
	protected void getActivityReportData(LocalDateTime timestamp) {
		Account account = super.getAccount();
		List<ActivityData> activities = account.getActivitiesDataFrom(timestamp);
		
		for (ActivityData data : activities) {
			System.out.println("Date: "+ getDate(data.getTimeStamp()) + String.format(" , change in balance: %.2f",  data.getBalanceChange())  + " , info: " + data.getInfo());
		}
		
		Menu.printNewLine();
		checkBalance();	
	}
	
	private LocalDate getDate(LocalDateTime timestamp) {
		return LocalDate.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDayOfMonth());
	}

	/**
	 * Gets the fee payment and updates the balance.
	 * @param activityName  the type of the activity that was made.
	 * @param fee the amount of the payment.
	 */
	public void makeFeeCollectionPayBill(ActivityName activityName, double fee) {
		ActivityData activityData = new ActivityData(ActivityName.FEE_COLLECTION, LocalDateTime.now(), "fee paymente for: "+activityName.toString(), fee);
		Account account = super.getAccount();
	
		account.setBalance(account.getBalance() + fee);
		account.addActivityData(activityData);
	}
	
	/**
	 * Gets the bill payment and updates the balance.
	 * @param amount bill amount.
	 */
	public void getBillPayment(int amount) {
		ActivityData activityData = new ActivityData(ActivityName.DEPOSIT, LocalDateTime.now(), "received bill payment", amount);
		Account account = super.getAccount();
	
		account.setBalance(account.getBalance() + amount);
		account.addActivityData(activityData);
	}
	
	/**
	 * Gets loan amount that the manager has to give and withdrawal from the bank account.
	 * @param amount loan amount.
	 */
	public void getLoanFromBank(int amount) {
		ActivityData activityData = new ActivityData(ActivityName.WITHDRAWAL, LocalDateTime.now(), "gave a loan", -amount);
		Account account = super.getAccount();
	
		account.setBalance(account.getBalance() - amount);
		account.addActivityData(activityData);
	}
	
	@Override
	public void showMenu() {
		Menu.printManagerMenu();		
	}
	
	@Override
	public void chooseMenuItem(int input) {
		switch (input) {
		case 1:
			setAndApproveUsers();
			break;
		case 2:
			getActivityReport();
			break;
		}
	}
}
