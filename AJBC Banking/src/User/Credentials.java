package User;

public class Credentials {
	private String username;
	private String password;
	
	public Credentials(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	//TODO Logic setUsername
	private void setUsername(String username) {
		this.username = username;
	}

	//TODO Logic setPassword
	private void setPassword(String password) {
		this.password = password;
	}
	
	protected boolean isUsernameEqualls(String username) {
		return this.username.equals(username);
	}
	
	protected boolean isPasswordEqualls(String password) {
		return this.password.equals(password);
	}
}
