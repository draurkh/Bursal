package net.talhakumru.bursal;

import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "app_list")
public class ApplicationCollection {
	
	private List<ScholarshipApplication> applications;
	
	public ApplicationCollection() {
		
	}

	public List<ScholarshipApplication> getApplications() {
		applications = new RestController().getApplications().into(applications);
		if (applications == null) {
			System.out.println("it is null bro");
		} else {
			System.out.println("hmmm, it's not null it seems");
		}
		System.out.println(applications);
		return applications;
	}
	
}
