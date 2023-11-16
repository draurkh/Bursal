package net.talhakumru.bursal;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.lang.NonNull;

import net.talhakumru.bursal.Constants.State;

public class RestController {
	private MongoCollection<Document> admins;
	private MongoCollection<Document> applications;

	public RestController() {
		MongoDatabase database = MongoInstance.getMongoDatabase();
		admins = database.getCollection("admins");
		applications = database.getCollection("applications");
	}

	// sends the submitted application to remote database
	public String sendApplication(@NonNull ApplicationDocument application) {

		if (application == null)
			throw new NullPointerException("Application cannot be null.");

		Document newDocument = application.appToDoc();
		newDocument.put("state", State.UNDECIDED.toString());

		System.out.println("Application to Document:");
		System.out.println(newDocument);

		try {
			InsertOneResult result = applications.insertOne(newDocument);

			System.out.println("Success! Inserted document id: " + result.getInsertedId());

			return "positive_response_view.xhtml?faces-redirect=true&id="
					+ result.getInsertedId().asObjectId().getValue().toString();

		} catch (Exception e) {
			System.err.println("Unable to insert due to an error: " + e);

			return "negative_response_view.xhtml?faces-redirect=true";
		}

		// Firebase Code - not working
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
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	// gets all applications stored in the database
	public List<ApplicationDocument> getApplications() {
		ArrayList<ApplicationDocument> apps = new ArrayList<>();
		List<Document> list = applications.find().into(new ArrayList<>());

		for (Iterator<Document> doc = list.iterator(); doc.hasNext();) {
			apps.add(new ApplicationDocument(doc.next()));
		}
		return apps;
	}

	// validates admin credentials
	public String loginAsAdmin(String email, String password) {

		Document admin = admins.find(eq("email", email)).first();
		System.out.println(admin);

		if (admin != null && password.equals(admin.getString("password"))) {
			System.out.println("admin has logged on!");
			return "admin_controls?faces-redirect=true";
		}

		return null;

		// Firebase Code - not working
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
		 */
	}

	// redirects to application_details page with given id
	public String goToDetails(String id) {
		ApplicationCollection.current = new ApplicationDocument(applications.find(eq(new ObjectId(id))).first());
		return "application_details.xhtml";
	}

	// approval state is changed
	public String changeState(ObjectId id, State state) {
		System.out.println("entered RestController.changeState() with state: " + state);
		Document document = applications.findOneAndUpdate(eq(id), Updates.set("state", state.toString()));
		System.out.println("found document: " + document);
		return "admin_controls?faces-redirect=true";
	}
}
