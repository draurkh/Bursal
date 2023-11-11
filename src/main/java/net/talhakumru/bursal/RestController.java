package net.talhakumru.bursal;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.lang.NonNull;

public class RestController {
	private MongoCollection<Document> admins;
	private MongoCollection<Document> applications;
	
	public RestController() {
		MongoDatabase database = MongoInstance.getMongoDatabase();
		admins = database.getCollection("admins");
		applications  = database.getCollection("applications");
	}

	public String sendApplication(@NonNull ApplicationDocument application) {
		
		if (application == null) throw new NullPointerException("Application cannot be null.");
		
		System.out.println("Application:");
		System.out.println(application);
		
		Document newDocument = new Document()
				.append("firstName", application.getFirstName()) 
				.append("lastName", application.getLastName())
				.append("birthday", application.getBirthday()) 
				.append("university", application.getUniversity()) 
				.append("address", application.getAddress());
		
		System.out.println("Application to Document:");
		System.out.println(newDocument);
		
		try {
			InsertOneResult result = applications.insertOne(newDocument);
			
			System.out.println("Success! Inserted document id: " + result.getInsertedId());
			
			return "response_view.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
			System.err.println("Unable to insert due to an error: " + e);
		}
		
		
		// Firebase Code
		/*
		 * URI uri; try { uri = new URI(Constants.REQUEST_URL + "?auth=" +
		 * Constants.API_KEY);
		 * 
		 * HttpRequest request = HttpRequest.newBuilder() .uri(uri)
		 * .POST(BodyPublishers.ofString(jsonRequest)) .build();
		 * 
		 * System.out.println(jsonRequest);
		 * 
		 * HttpClient client = HttpClient.newHttpClient(); client.sendAsync(request,
		 * BodyHandlers.ofString()) .thenApply(HttpResponse::body)
		 * .thenAccept(System.out::println) .join();
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
			
		return null;
	}
	
	public List<ApplicationDocument> getApplications() {
		//ArrayList<ScholarshipApplication> list = applications.find().into(new ArrayList<ScholarshipApplication>());
		/*
		 * for (ScholarshipApplication application : applications.find()) {
		 * list.add(application); }
		 */
		ArrayList<ApplicationDocument> apps = new ArrayList<ApplicationDocument>() ;
		List<Document> list = applications.find().into(new ArrayList<Document>());
		
		for (Iterator<Document> doc = list.iterator(); doc.hasNext();) {
			apps.add(new ApplicationDocument(doc.next()));
		}
		
		System.out.println("list after: " + list);
		System.out.println("apps after: " + apps);
		return apps;
	}
	
	public String loginAsAdmin(String email, String password) {
		
		Document admin = admins.find(eq("email", email)).first();
		System.out.println(admin);
		
		if (admin != null && password.equals(admin.getString("password"))) {
			return "admin_controls?faces-redirect=true";
		} 
		
		return null;
		
		
		// Firebase Code
		/*
		 * //URI uri;
		 * 
		 * try { //URI uri1 = new URI("https", null,
		 * "//bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app.json", "auth="
		 * + Constants.API_KEY + "&orderBy=\"email\"&equalTo=\"" + email + "\"", null);
		 * 
		 * URI uri2 = new URI("https", null,
		 * "//bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app/admins.json",
		 * "auth=" + Constants.API_KEY + "&orderBy=\"email\"&equalTo=\"" + email + "\"",
		 * null); System.out.println("URL: " + uri2.toASCIIString());
		 * 
		 * HttpRequest request = HttpRequest.newBuilder() .uri(uri2) .build();
		 * 
		 * 
		 * HttpClient client = HttpClient.newHttpClient(); HttpResponse<String> response
		 * = client.send(request, BodyHandlers.ofString());
		 * 
		 * System.out.println(response.body());
		 * 
		 * try { System.out.println(System.getProperty("user.dir"));
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}
