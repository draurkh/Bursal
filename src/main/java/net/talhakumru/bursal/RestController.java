package net.talhakumru.bursal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.Authenticator;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class RestController {

	public String sendApplication(String jsonRequest) {
		
		URI uri;
		try {
			uri = new URI(Constants.REQUEST_URL + "?auth=" + Constants.API_KEY);
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(uri)
					.POST(BodyPublishers.ofString(jsonRequest))
					.build();
			
			System.out.println(jsonRequest);
			
			HttpClient client = HttpClient.newHttpClient();
			client.sendAsync(request, BodyHandlers.ofString())
		    	.thenApply(HttpResponse::body)
				.thenAccept(System.out::println)
				.join();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return null;
	}
	
	public String loginAsAdmin(String email) {
		//URI uri;
		
		try {
			//URI uri1 = new URI("https", null, "//bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app.json", "auth=" + Constants.API_KEY + "&orderBy=\"email\"&equalTo=\"" + email + "\"", null);

			URI uri2 = new URI("https", null, "//bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app/admins.json", "auth=" + Constants.API_KEY + "&orderBy=\"email\"&equalTo=\"" + email + "\"", null);
			System.out.println("URL: " + uri2.toASCIIString());
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(uri2)
					.build();
			
			
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			System.out.println(response.body());
			
			try {
				System.out.println(System.getProperty("user.dir"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
