package net.talhakumru.bursal;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.catalina.core.ApplicationPart;
import org.bson.Document;

@ManagedBean(name = "app")
@SessionScoped
public class ScholarshipApplication {
	
	private final short CV_FILE_SIZE_LIMIT = 5120;
	
	private String firstName;
	private String lastName;
	private String birthday;
	private String university;
	private String address;
	private ApplicationPart cv;
	
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

	public ApplicationPart getCv() {
		return cv;
	}

	public void setCv(ApplicationPart cv) {
		this.cv = cv;
	}

	public String sendApplication() {
		if (cv != null) {
			System.out.println(cv);
			System.out.println(cv.getContentType());
			System.out.println(cv.getName());
			System.out.println(cv.getSize());
			System.out.println(cv.getSubmittedFileName());
			System.out.println(cv.getHeaderNames());
			
			if (cv.getSize() > CV_FILE_SIZE_LIMIT) {
				return null;
			}
		}
		
		return new RestController().sendApplication(this);
	}

	@Override
	public String toString() {
		return  "Application [\n  firstName=" + firstName + ",\n  lastName=" + lastName + "\n  birthday=" + birthday + "\n  university=" + university + "\n  addres=" + address + "\n]";
	}
	
	
}
