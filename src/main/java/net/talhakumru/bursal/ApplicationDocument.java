package net.talhakumru.bursal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.bson.Document;
import org.bson.types.ObjectId;

import net.talhakumru.bursal.Constants.State;

@SessionScoped
@ManagedBean(name = "appdoc")
public class ApplicationDocument {
	
	private final short CV_FILE_SIZE_LIMIT = 5; // MB
	
	private ObjectId id;
	private State state;
	private String firstName;
	private String lastName;
	private String birthday;
	private String university;
	private String address;
	//private ApplicationPart cv;
	
	public ApplicationDocument() {
		
	}
	
	public ApplicationDocument(Document doc) {
		id = doc.getObjectId("_id");
		firstName = doc.getString("firstName");
		lastName = doc.getString("lastName");
		birthday = doc.getString("birthday");
		university = doc.getString("university");
		address = doc.getString("address");
		state = State.valueOf(doc.getString("state"));
	}

	public ObjectId get_id() {
		return id;
	}

	public void set_id(ObjectId _id) {
		this.id = _id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	/*
	 * public ApplicationPart getCv() { return cv; }
	 * 
	 * public void setCv(ApplicationPart cv) { this.cv = cv; }
	 */

	public String sendApplication() {
		/*
		 * if (cv != null) { System.out.println(cv);
		 * System.out.println(cv.getContentType()); System.out.println(cv.getName());
		 * System.out.println(cv.getSize());
		 * System.out.println(cv.getSubmittedFileName());
		 * System.out.println(cv.getHeaderNames());
		 * 
		 * if (!checkFileSize(cv.getSize())) { return null; } }
		 */
		
		return new RestController().sendApplication(this);
	}
	
	private boolean checkFileSize(long size) {
		int sizeInMBs = (int) size / 1024 / 1024;
		return sizeInMBs < CV_FILE_SIZE_LIMIT;
	}

	public String toListEntry() {
		return capitalize(firstName) + " " + capitalize(lastName);
	}
	
	private String capitalize(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
	}
	
	@Override
	public String toString() {
		return  "Application [\n _id=" + id + "\n  firstName=" + firstName + ",\n  lastName=" + lastName + "\n  birthday=" + birthday + "\n  university=" + university + "\n  address=" + address + "\n  state=" + state.toString() + "\n]";
	}
	
	
}
