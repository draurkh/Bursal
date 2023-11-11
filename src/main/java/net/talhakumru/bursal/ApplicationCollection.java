package net.talhakumru.bursal;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "app_coll")
public class ApplicationCollection {
	
	private ArrayList<ApplicationDocument> applications;
	private List<String> listEntries;
	
	public ApplicationCollection() {
		applications = new ArrayList<ApplicationDocument>();
		listEntries = new ArrayList<String>();
	}
	
	public ArrayList<ApplicationDocument> getApplications() {
		setApplications();
		return applications;
	}

	private void setApplications() {
		applications.clear();
		applications.addAll(new RestController().getApplications());
		if (applications == null) {
			System.out.println("it is null bro");
		} else {
			System.out.println("hmmm, it's not null it seems");
		}
		System.out.println(applications);
	}
	
	public List<String> getListEntries() {
		setListEntries();
		return listEntries;
	}

	private void setListEntries() {
		setApplications();
		listEntries.clear();
		for (int i = 0; i < applications.size(); i++) {
			listEntries.add(applications.get(i).toListEntry());
		}
		System.out.println(listEntries);
	}
	
}
