package User;

import java.util.Objects;

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


	// TODO PhoneNumber equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneNumber other = (PhoneNumber) obj;
		return Objects.equals(body, other.body) && Objects.equals(preffix, other.preffix);
	}
	
	
}