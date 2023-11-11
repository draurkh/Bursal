package net.talhakumru.bursal;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "app_list")
public class ApplicationCollection {
	
	private ArrayList<ScholarshipApplication> applications;
	private List<String> listEntries;
	
	public ApplicationCollection() {
		applications = new ArrayList<ScholarshipApplication>();
		listEntries = new ArrayList<String>();
	}

	public void setApplications() {
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
		setApplications();
		setListEntries();
		return listEntries;
	}

	public void setListEntries() {
		listEntries.clear();
		for (int i = 0; i < applications.size(); i++) {
			listEntries.add(applications.get(i).toListEntry());
		}
		System.out.println(listEntries);
	}
	
}
