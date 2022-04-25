package User;

public class Credentials {
	private String username;
	private String password;
	
	public Credentials(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	//TODO Logic
	private void setUsername(String username) {
		this.username = username;
	}

	//TODO Logic
	private void setPassword(String password) {
		this.password = password;
	}
}
