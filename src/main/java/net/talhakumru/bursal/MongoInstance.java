package net.talhakumru.bursal;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoInstance {

	private final String CONNECTION_URI = "mongodb+srv://bursal-webapp:" + Constants.MONGO_CLUSTER_PASSWD
			+ "@bursal-cluster.6aonal5.mongodb.net/?retryWrites=true&w=majority";
	private static MongoDatabase mongoDatabase = null;
	private MongoClient mongoClient;

	private MongoInstance() {
		// to enable POJO
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		mongoClient = MongoClients.create(CONNECTION_URI);
		try {
			mongoDatabase = mongoClient.getDatabase("bursal").withCodecRegistry(pojoCodecRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// only one instance of database is allowed
	public static MongoDatabase getMongoDatabase() {
		if (mongoDatabase == null) {
			new MongoInstance();
		}

		return mongoDatabase;
	}
}
