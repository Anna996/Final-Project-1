package Account;

public enum Payee {
	AJBC_BANK, PHONE_COMPANY, WATER_COMPANY, ELECTRIC_COMPANY;

	public static Payee getPayee(int id) {
		switch (id) {
		case 1:
			return AJBC_BANK;
		case 2:
			return PHONE_COMPANY;
		case 3:
			return WATER_COMPANY;
		case 4:
			return ELECTRIC_COMPANY;
		default:
			return AJBC_BANK;
		}
	}

	public static int getId(Payee payee) {
		switch (payee) {
		case AJBC_BANK:
			return 1;
		case PHONE_COMPANY:
			return 2;
		case WATER_COMPANY:
			return 3;
		case ELECTRIC_COMPANY:
			return 4;
		default:
			return 1;
		}
	}
}
