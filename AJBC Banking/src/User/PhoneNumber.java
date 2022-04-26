package User;

public class PhoneNumber {
	private String preffix;
	private String body;

	private static final int PREFFIX_SIZE = 3;
	private static final int BODY_SIZE = 7;

	public PhoneNumber(String preffix, String body) {
		setPreffix(preffix);
		setBody(body);
	}

	private void setPreffix(String preffix) {
		this.preffix = isValidtNumberPart(preffix, "preffix", PREFFIX_SIZE) ? preffix : null;
	}

	private void setBody(String body) {
		this.body = isValidtNumberPart(body, "body", BODY_SIZE) ? body : null;
	}

	// Checks if the phone-part is of a required length and contains only digits.
	private static boolean isValidtNumberPart(String numberPart, String name, int size) {
		if (numberPart.length() != size) {
			printErrorMsg(name, size);
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (!Character.isDigit(numberPart.charAt(i))) {
				printErrorMsg(name, size);
				return false;
			}
		}

		return true;
	}

	private static void printErrorMsg(String name, int size) {
		System.out.printf("PhoneNumber %s has to be of size %d and only digits\n", name, size);
	}

	/**
	 * Creates new instance of PhoneNumber with the data it got from the string parameter.
	 * @param phoneNumber string of required data to create PhoneNumber.
	 * @return if the string is valid, new instance of PhoneNumber, otherwise returns null.
	 */
	public static PhoneNumber getPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() != PREFFIX_SIZE + BODY_SIZE) {
			System.out.println("Invalid phone number");
			return null;
		}
		String preffix = phoneNumber.substring(0, PREFFIX_SIZE);
		String body = phoneNumber.substring(PREFFIX_SIZE, PREFFIX_SIZE + BODY_SIZE);
		
		if(!isValidtNumberPart(preffix,"preffix",PREFFIX_SIZE) || !isValidtNumberPart(body,"body",BODY_SIZE)) {
			return null;
		}
		return new PhoneNumber(preffix, body);
	}

	public boolean equals(PhoneNumber other) {
		return preffix.equals(other.preffix) && body.equals(other.body);
	}
}