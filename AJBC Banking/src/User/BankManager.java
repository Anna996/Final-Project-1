package User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
		super(phoneNumber, firstName, lastName, birthDate, credentials, 0);
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

		Account account = new Account(properties, interestRate, operationFee, this);

		user.setAccount(account);
	}

	private void setBankAccount() {
		Account account = new Account(AccountProperties.TITANIUM, 0, 0, this);
		setAccount(account);
	}

	@Override
	protected void getActivityReportData(LocalDateTime timestamp) {
		Account account = super.getAccount();
		ActivityData[] activities = account.getActivitiesDataFrom(timestamp);
		
		for (ActivityData data : activities) {
			System.out.println("Date: "+ getDate(data.getTimeStamp()) + " , change in balance: " + data.getBalanceChange() + " , info: " + data.getInfo());
		}
		
		Menu.printNewLine();
		checkBalance();	
	}
	
	private LocalDate getDate(LocalDateTime timestamp) {
		return LocalDate.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDayOfMonth());
	}

	public void makeFeeCollectionPayBill(ActivityName activityName, double fee) {
		ActivityData activityData = new ActivityData(ActivityName.FEE_COLLECTION, LocalDateTime.now(), "fee paymente: "+activityName.toString(), fee);
		Account account = super.getAccount();
	
		account.setBalance(account.getBalance() + fee);
		account.addActivityData(activityData);
	}
	
	public void getBillPayment(int amount) {
		ActivityData activityData = new ActivityData(ActivityName.DEPOSIT_CASH, LocalDateTime.now(), "received bill payment", amount);
		Account account = super.getAccount();
	
		account.setBalance(account.getBalance() + amount);
		account.addActivityData(activityData);
	}
}
