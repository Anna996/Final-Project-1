package Account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoanTest {

	@Test
	void checkMonthlyPayment() {
		Loan loan = new Loan(1000, 10);
		float numOfPayments = (1000 * 1.05f) / 10;
		assertEquals(numOfPayments, loan.getMonthlyPayment());
	}

}
