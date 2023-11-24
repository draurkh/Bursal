package net.talhakumru.bursal;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class IndexRedirect {
	
	
	
	public IndexRedirect() {
		
	}
	
	public void redirect() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("application.xhtml");
	}
}
