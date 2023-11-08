package net.talhakumru.bursal;

import javax.faces.bean.ManagedBean;

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
	
	
	
}
