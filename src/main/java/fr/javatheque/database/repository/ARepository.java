package fr.javatheque.database.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * ARepository is an abstract class representing a generic repository for MongoDB.
 */
public abstract class ARepository {

    private static final String DATABASE_NAME = "javatheque";
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost:27017";
    private static volatile MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private final MongoCollection<Document> collection;

    /**
     * Constructs a new ARepository instance with the specified collection name.
     * Initializes the MongoDB client and database if not already initialized.
     *
     * @param collectionName The name of the collection to be used.
     */
    public ARepository(String collectionName) {
        if (mongoClient == null) {
            synchronized (ARepository.class) {
                if (mongoClient == null) {
                    mongoClient = MongoClients.create(CONNECTION_STRING);
                    mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
                }
            }
        }
        this.collection = mongoDatabase.getCollection(collectionName);
    }

    /**
     * Retrieves the MongoDB database associated with this repository.
     *
     * @return The MongoDB database instance.
     */
    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    /**
     * Retrieves the MongoDB collection associated with this repository.
     *
     * @return The MongoDB collection instance.
     */
    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
