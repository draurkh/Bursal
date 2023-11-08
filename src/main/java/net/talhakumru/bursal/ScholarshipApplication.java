package net.talhakumru.bursal;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "app")
public class ScholarshipApplication {
	
	private String firstName;
	private String lastName;
	private String birthday;
	private String university;
	private String address;
	
	public ScholarshipApplication() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
