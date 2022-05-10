package Account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class AccountPropertiesTest {
	private AccountProperties bronze;
	private AccountProperties silver;
	private AccountProperties gold;
	private AccountProperties titanium;
	
	public AccountPropertiesTest() {
		bronze = AccountProperties.BRONZE;
		silver = AccountProperties.SILVER;
		gold = AccountProperties.GOLD;
		titanium = AccountProperties.TITANIUM;
	}

	@Test
	void testIsInterestRateInRagne() {
		assertTrue(bronze.isInterestRateInRagne(5f));
		assertFalse(bronze.isInterestRateInRagne(4.4f));
		assertFalse(bronze.isInterestRateInRagne(6.1f));
		
		assertTrue(silver.isInterestRateInRagne(3.2f));
		assertFalse(silver.isInterestRateInRagne(2.9f));
		assertFalse(silver.isInterestRateInRagne(4.6f));
		
		assertTrue(gold.isInterestRateInRagne(2f));
		assertFalse(gold.isInterestRateInRagne(1.4f));
		assertFalse(gold.isInterestRateInRagne(3.1f));
		
		assertTrue(titanium.isInterestRateInRagne(0));
		assertFalse(titanium.isInterestRateInRagne(-0.2f));
		assertFalse(titanium.isInterestRateInRagne(1f));
	}

	@Test
	void testIsOperationFeeInRagne() {
		assertTrue(bronze.isOperationFeeInRagne(5.2f));
		assertFalse(bronze.isOperationFeeInRagne(4.4f));
		assertFalse(bronze.isOperationFeeInRagne(7.6f));
		
		assertTrue(silver.isOperationFeeInRagne(4f));
		assertFalse(silver.isOperationFeeInRagne(2.9f));
		assertFalse(silver.isOperationFeeInRagne(5.6f));
		
		assertTrue(gold.isOperationFeeInRagne(2f));
		assertFalse(gold.isOperationFeeInRagne(1.74f));
		assertFalse(gold.isOperationFeeInRagne(3.9f));
		
		assertTrue(titanium.isOperationFeeInRagne(0));
		assertFalse(titanium.isOperationFeeInRagne(-0.2f));
		assertFalse(titanium.isOperationFeeInRagne(1f));
	}
}
