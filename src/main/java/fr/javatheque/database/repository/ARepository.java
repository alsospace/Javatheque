package fr.javatheque.database.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public abstract class ARepository {

    private static final String DATABASE_NAME = "javatheque";
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost:27017";
    private static volatile MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private final MongoCollection<Document> collection;

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

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}