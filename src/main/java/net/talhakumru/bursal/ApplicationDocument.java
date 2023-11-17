package net.talhakumru.bursal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.shared.renderkit.html.util.HttpPartWrapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import net.talhakumru.bursal.Constants.State;

@ManagedBean(name = "appdoc")
@SessionScoped
public class ApplicationDocument {

	private final short CV_FILE_SIZE_LIMIT = 5; // MB

	private ObjectId id;
	private State state = State.UNDECIDED;
	private String firstName;
	private String lastName;
	private String birthday;
	private String university;
	private String address;
	private HttpPartWrapper cvFile;
	private String cvFileName;

	public ApplicationDocument() {

	}

	// Copy Constructor
	public ApplicationDocument(Document doc) {
		id = doc.getObjectId("_id");
		firstName = doc.getString("firstName");
		lastName = doc.getString("lastName");
		birthday = doc.getString("birthday");
		university = doc.getString("university");
		address = doc.getString("address");
		state = State.valueOf(doc.getString("state"));
		cvFileName = doc.getString("cv_file_name");
	}

	// GETTERS and SETTERS

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public HttpPartWrapper getCvFile() {
		return cvFile;
	}

	public void setCvFile(HttpPartWrapper cvFile) {
		this.cvFile = cvFile;
	}

	public String getCvFileName() {
		return cvFileName;
	}

	// CV file is validated for the file type and the file size
	public void validateCV(FacesContext context, UIComponent component, Object value) {
		System.out.println("entered validateCV");

		HttpPartWrapper file = (HttpPartWrapper) value;
		int sizeInMBs = (int) (file.getSize() / 1024 / 1024);

		if (!file.getContentType().split("/", 2)[0].equals("image")
				&& !file.getContentType().equals("application/pdf")) {
			// file type is incorrect

			((UIInput) component).setValid(false);

			FacesMessage typeError = new FacesMessage(file.getSubmittedFileName()
					+ " dosya tipi kabul edilemez. Lütfen PDF veya bir görsel dosyası yükleyin. (.pdf veya .png, .jpg gibi)");
			typeError.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(component.getClientId(context), typeError);
		}

		if (sizeInMBs >= CV_FILE_SIZE_LIMIT) {
			// file size is too big

			((UIInput) component).setValid(false);

			FacesMessage sizeError = new FacesMessage(file.getSubmittedFileName()
					+ " dosyasının boyutu çok büyük. Lütfen 5 Megabayt'tan (MB) daha küçük bir dosya yükleyin.");
			sizeError.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(component.getClientId(context), sizeError);
		}
	}

	// sends the submitted application to remote database
	public String send() {
		System.out.println("entered sendApplication()");
		saveFile();

		return new RestController().sendApplication(this);
	}

	// saves uploaded CV files
	private void saveFile() {
		System.out.println("entered saveFile()");

		System.out.println("content type: " + cvFile.getContentType());
		String[] contentType = cvFile.getContentType().split("/", 2);

		try (InputStream input = cvFile.getInputStream()) {

			String fileExtension = contentType[contentType.length - 1];
			cvFileName = new CVFileName().get(firstName, lastName, fileExtension); // creates file names with global
																					// counter
			System.out.println("File name: " + cvFileName);

			// files are saved locally to <user.home>/cv_files
			File cv_dir = new File(Constants.CV_DIR);
			cv_dir.mkdirs();
			
			File savedFile = new File(cv_dir.getAbsolutePath(), cvFileName);
			System.out.println("Saved file to path: " + savedFile.getAbsolutePath());

			Files.copy(input, savedFile.toPath());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String downloadCV() {
		File file = new File(Constants.CV_DIR + System.getProperty("file.separator") + this.cvFileName);
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext exContext = context.getExternalContext();

		exContext.responseReset();
		exContext.setResponseContentType(exContext.getMimeType(file.getName()));
		exContext.setResponseContentLength((int) file.length());
		exContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); // enables
																												// downloading
																												// attached
																												// file

		try {
			OutputStream outputStream = exContext.getResponseOutputStream();
			Files.copy(file.toPath(), outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		context.responseComplete();
		return null;
	}

	// ApplicationDocument to Document conversion
	public Document appToDoc() {
		System.out.println("entered: appToDoc()");
		return new Document().append("firstName", this.getFirstName()).append("lastName", this.getLastName())
				.append("birthday", this.getBirthday()).append("university", this.getUniversity())
				.append("address", this.getAddress()).append("state", this.state).append("cv_file_name", cvFileName);
	}

	// Admin can approve the application
	public String approve() {
		return new RestController().changeState(id, State.APPROVED);
	}

	// Admin can deny the application
	public String deny() {
		return new RestController().changeState(id, State.DENIED);
	}

	// Full name is capitalized
	public String toListEntry() {
		return capitalize(firstName) + " " + capitalize(lastName);
	}

	private String capitalize(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
	}

	@Override
	public String toString() {
		return "Application [\n _id=" + id + "\n  firstName=" + firstName + ",\n  lastName=" + lastName
				+ "\n  birthday=" + birthday + "\n  university=" + university + "\n  address=" + address + "\n  state="
				+ state.toString() + "\n]";
	}

}
