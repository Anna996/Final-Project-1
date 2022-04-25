package User;

public class PhoneNumber {
	private String preffix; // 3 digits
	private String body; // 7 digits

	public PhoneNumber(String preffix, String body) {
		this.preffix = preffix;
		this.body = body;
	}

	public String getPhoneNumber() {
		return String.format("%s-%s", preffix, body);
	}
	
	//TODO getPhoneNumber by string
	public static PhoneNumber getPhoneNumber(String phoneNumber) {
		return new PhoneNumber("", "");
	}
}