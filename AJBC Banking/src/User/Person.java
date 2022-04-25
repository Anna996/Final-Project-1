package User;

import java.time.LocalDate;

public abstract class Person {
	private PhoneNumber phoneNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;

	public Person(PhoneNumber phoneNumber, String firstName, String lastName, LocalDate birthDate) {
		setPhoneNumber(phoneNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setBirthDate(birthDate);
	}

	private void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	
	
}
