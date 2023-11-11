package net.talhakumru.bursal;

import static com.mongodb.client.model.Filters.eq;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.lang.NonNull;

public class RestController {
	private MongoCollection<Admin> admins;
	private MongoCollection<ScholarshipApplication> applications;
	
	public RestController() {
		MongoDatabase database = MongoInstance.getMongoDatabase();
		admins = database.getCollection("admins", Admin.class);
		applications  = database.getCollection("applications", ScholarshipApplication.class);
	}

	public String sendApplication(@NonNull ScholarshipApplication application) {
		
		System.out.println(application);
		
		
		try {
			InsertOneResult result = applications.insertOne(application);
			
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
	
	public FindIterable<ScholarshipApplication> getApplications() {
		return applications.find();
	}
	
	public String loginAsAdmin(String email, String password) {
		
		Admin admin = admins.find(eq("email", email)).first();
		System.out.println(admin);
		
		if (admin != null && password.equals(admin.getPassword())) {
			return "/admin_controls?faces-redirect=true";
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
