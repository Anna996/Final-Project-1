package Account;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import ActivityData.ActivityData;
import ActivityData.ActivityName;
import User.BankManager;
import User.Credentials;
import User.PhoneNumber;

@TestInstance(Lifecycle.PER_METHOD)
class AccountTest {
	private Account account;
	private BankManager manager;
	
	private final float FEE;
	private final int AMOUNT = 200;
	private final int LOAN_AMOUNT = 10000;
	private final int NUM_OF_PAYMENTS = 10;

	public AccountTest() {
		account = new Account(AccountProperties.BRONZE, 5f, 6f);
		manager = createBankManager();
		account.setManager(manager);
		FEE = 6;
	}

	private BankManager createBankManager() {
		PhoneNumber phoneNumber = new PhoneNumber("054", "5555555");
		LocalDate birthDate = LocalDate.of(1960, 10, 27);
		Credentials credentials = new Credentials("m", "mm11");
		return new BankManager(phoneNumber, "Avi", "Levi", birthDate, credentials);
	}

	void testBalanceAfterFee(double expected) {
		assertEquals(expected - FEE, account.getBalance());
	}

	@Test
	void testGetBalance() {
		assertEquals(0, account.getBalance());
		account.setBalance(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void testGetManager() {
		assertEquals(manager, account.getManager());
	}

	void testActivityAfterOperation(ActivityName activityName, String info, double balanceChange) {
		ActivityData[] activities = account.getActivities();
		assertEquals(activityName, activities[0].getActivityName());
		assertEquals(info, activities[0].getInfo());
		assertEquals(balanceChange, activities[0].getBalanceChange());
	}

	void testActivityAfterFee() {
		ActivityData[] activities = account.getActivities();
		assertEquals(ActivityName.FEE_COLLECTION, activities[1].getActivityName());
		assertEquals("Fee operation to bank", activities[1].getInfo());
		assertEquals(-FEE, activities[1].getBalanceChange());
	}

	@Test
	void testDepositCash() {
		account.depositCash(AMOUNT);
		testBalanceAfterFee(AMOUNT);
		testActivityAfterOperation(ActivityName.DEPOSIT, "none", AMOUNT);
		testActivityAfterFee();
	}

	@Test
	void testWithdrawalCash() {
		assertTrue(account.withdrawalCash(AMOUNT));
		testBalanceAfterFee(-AMOUNT);
		assertFalse(account.withdrawalCash(2501));
		testBalanceAfterFee(-AMOUNT);
		testActivityAfterOperation(ActivityName.WITHDRAWAL, "none", -AMOUNT);
		testActivityAfterFee();
	}

	@Test
	@DisplayName("Check if I can transfer amount from my account to another")
	void checkTransfer() {
		assertTrue(account.transfer(AMOUNT));
		testBalanceAfterFee(-AMOUNT);
		assertFalse(account.transfer(2001));
		testActivityAfterOperation(ActivityName.MAKE_PAYMENT_TRANSFER, "transfer to another user", -AMOUNT);
		testActivityAfterFee();
	}

	@Test
	@DisplayName("Check if I can get a transferred amount from other account")
	void checkTransferredToMe() {
		account.transferredToMe(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
		testActivityAfterOperation(ActivityName.MAKE_PAYMENT_TRANSFER, "transferred from other user", AMOUNT);
	}

	@Test
	void testPayBillToTheBank() {
		assertTrue(account.payBill(AMOUNT, Payee.AJBC_BANK.toString()));
		testBalanceAfterFee(-AMOUNT);
		assertFalse(account.payBill(5001, Payee.AJBC_BANK.toString()));
		testBalanceAfterFee(-AMOUNT);
		testActivityAfterOperation(ActivityName.PAY_BILL, "Payee: " + Payee.AJBC_BANK.toString(), -AMOUNT);
		testActivityAfterFee();

		// check the account of the bank
		Account managerAccount = manager.getAccount();
		assertEquals(FEE + AMOUNT, managerAccount.getBalance());

		ActivityData activity = managerAccount.getActivities()[1];
		assertEquals(ActivityName.DEPOSIT, activity.getActivityName());
		assertEquals("received bill payment", activity.getInfo());
		assertEquals(AMOUNT, activity.getBalanceChange());
	}

	@Test
	void checkIsLoanAmountAcceptable() {
		assertTrue(account.isLoanAmountAcceptable(LOAN_AMOUNT));
		assertFalse(account.isLoanAmountAcceptable(LOAN_AMOUNT + 1));
	}

	@Test
	void testGetLoan() {
		account.getLoan(LOAN_AMOUNT, NUM_OF_PAYMENTS);
		testBalanceAfterFee(LOAN_AMOUNT);
		testActivityAfterOperation(ActivityName.GET_LOAN, "numOfPayments: " + NUM_OF_PAYMENTS, LOAN_AMOUNT);
		testActivityAfterFee();

		// check the account of the bank
		Account managerAccount = manager.getAccount();
		assertEquals(FEE - LOAN_AMOUNT, managerAccount.getBalance());

		ActivityData activity = managerAccount.getActivities()[1];
		assertEquals(ActivityName.WITHDRAWAL, activity.getActivityName());
		assertEquals("gave a loan", activity.getInfo());
		assertEquals(-LOAN_AMOUNT, activity.getBalanceChange());
	}
	
	@Test
	void testGetActivitiesDataFrom() {
		LocalDateTime timestemp = LocalDateTime.now();
		account.depositCash(AMOUNT + AMOUNT);
		account.withdrawalCash(AMOUNT);
		
		ActivityData[] activities = account.getActivitiesDataFrom(timestemp);
		
		assertEquals(ActivityName.DEPOSIT, activities[0].getActivityName());
		assertEquals("none", activities[0].getInfo());
		assertEquals(AMOUNT + AMOUNT, activities[0].getBalanceChange());
		
		assertEquals(ActivityName.FEE_COLLECTION, activities[1].getActivityName());
		assertEquals("Fee operation to bank", activities[1].getInfo());
		assertEquals(-FEE, activities[1].getBalanceChange());
		
		assertEquals(ActivityName.WITHDRAWAL, activities[2].getActivityName());
		assertEquals("none", activities[2].getInfo());
		assertEquals(-AMOUNT, activities[2].getBalanceChange());
		
		assertEquals(ActivityName.FEE_COLLECTION, activities[3].getActivityName());
		assertEquals("Fee operation to bank", activities[3].getInfo());
		assertEquals(-FEE, activities[3].getBalanceChange());
	}
}
