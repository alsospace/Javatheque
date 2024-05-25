package fr.javatheque.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import fr.javatheque.database.codec.LibraryCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * This class provides methods to establish a connection to MongoDB
 * and retrieve database instances.
 */
public class MongoDBConnection {

    private static final String CONNECTION_STRING = "mongodb://root:root@localhost:27017";
    private static MongoClient mongoClient = null;

    // CodecRegistry for custom codecs
    private static final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromCodecs(new LibraryCodec())
    );

    /**
     * Retrieves the MongoClient instance, creating it if it doesn't exist.
     *
     * @return The MongoClient instance.
     */
    private static MongoClient getMongoClient() {
        if (mongoClient == null) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .codecRegistry(codecRegistry)
                    .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }

    /**
     * Retrieves the MongoDatabase instance for the specified database name.
     *
     * @param dbName The name of the MongoDB database.
     * @return The MongoDatabase instance.
     */
    public static MongoDatabase getDatabase(String dbName) {
        return getMongoClient().getDatabase(dbName);
    }

    /**
     * Retrieves the MongoDatabase instance for the 'javatheque' database.
     *
     * @return The MongoDatabase instance for the 'javatheque' database.
     */
    public static MongoDatabase getJavathequeDatabase() {
        return getDatabase("javatheque");
    }
}