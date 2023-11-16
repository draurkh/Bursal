package net.talhakumru.bursal;

import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "app_coll")
@ApplicationScoped
public class ApplicationCollection {

	private ArrayList<ApplicationDocument> applications;
	public static ApplicationDocument current; // used when displaying only one application with details
	private RestController restController;

	public ApplicationCollection() {
		applications = new ArrayList<ApplicationDocument>();
		current = null;
		restController = new RestController();
	}

	public ArrayList<ApplicationDocument> getApplications() {
		setApplications();
		return applications;
	}

	public ApplicationDocument getCurrent() {
		return current;
	}

	private void setApplications() {
		applications.clear();
		applications.addAll(restController.getApplications());
	}

	public String goToDetails(String id) {
		System.out.println("Going to details with id: " + id);
		return restController.goToDetails(id);
	}

}
