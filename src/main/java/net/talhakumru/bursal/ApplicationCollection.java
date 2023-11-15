package net.talhakumru.bursal;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

@ManagedBean(name = "app_coll")
@ApplicationScoped
public class ApplicationCollection {
	
	private ArrayList<ApplicationDocument> applications;
	public static ApplicationDocument current;
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
		if (applications == null) {
			System.out.println("it is null bro");
		} else {
			System.out.println("hmmm, it's not null it seems");
		}
		// System.out.println(applications);
	}
	
	public String goToDetails(String id) {
		System.out.println("Going to details with id: " + id);
		return restController.goToDetails(id);
	}
	
}
