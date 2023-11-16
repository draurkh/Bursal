package net.talhakumru.bursal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.bson.types.ObjectId;

@ManagedBean
@SessionScoped
public class Admin {
	private ObjectId _id;
	private String email;
	private String password;
	
	public Admin() {
		email = "talha.kumru767@gmail.com";
	}
	
	// GETTERS and SETTERS
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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
		return "Admin [\n  _id=" + _id +  "\n  email=" + email + ",\n  password=" + password + "\n]";
	}

	public String login() {
		RestController restController = new RestController();
		return restController.loginAsAdmin(email, password);
	}
	
}
