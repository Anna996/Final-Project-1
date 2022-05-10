package Account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayeeTest {

	@Test
	@DisplayName("check if getPayee returns the right Payee instance")
	void checkGetPayee() {
		assertEquals(Payee.AJBC_BANK, Payee.getPayee(1));
		assertEquals(Payee.PHONE_COMPANY, Payee.getPayee(2));
		assertEquals(Payee.WATER_COMPANY, Payee.getPayee(3));
		assertEquals(Payee.ELECTRIC_COMPANY, Payee.getPayee(4));;
		assertEquals(Payee.AJBC_BANK, Payee.getPayee(8));
	}

	@Test
	@DisplayName("check if getId returns the right index number")
	void checkGetId() {
		assertEquals(1, Payee.getId(Payee.AJBC_BANK));
		assertEquals(2, Payee.getId(Payee.PHONE_COMPANY));
		assertEquals(3, Payee.getId(Payee.WATER_COMPANY));
		assertEquals(4, Payee.getId(Payee.ELECTRIC_COMPANY));
	}
}
