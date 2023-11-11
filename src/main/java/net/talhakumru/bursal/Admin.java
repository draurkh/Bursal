package net.talhakumru.bursal;

import javax.faces.bean.ManagedBean;

import org.bson.types.ObjectId;

@ManagedBean
public class Admin {
	private String email;
	private String password;
	
	public Admin() {
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Admin [\n  email=" + email + ",\n  password=" + password + "\n]";
	}

	public String login() {
		RestController restController = new RestController();
		
		return restController.loginAsAdmin(email, password);
	}
	
}
