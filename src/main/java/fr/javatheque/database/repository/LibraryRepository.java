package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Library;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class LibraryRepository {

    private final MongoDatabase mongoDatabase;

    public LibraryRepository(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public Library createLibrary(Library library) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("libraries");
        Document document = MongoUtil.objectToDocument(library);
        collection.insertOne(document);
        return library;
    }

    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection("libraries");
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                libraries.add(MongoUtil.documentToObject(document, Library.class));
            }
        }
        return libraries;
    }

    public Library getLibraryByOwnerId(String ownerId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("libraries");
        Document document = collection.find(Filters.eq("ownerId", ownerId)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Library.class);
        }
        return null;
    }

    public void updateLibrary(Library library) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("libraries");
        Document document = MongoUtil.objectToDocument(library);
        collection.replaceOne(Filters.eq("ownerId", library.getOwnerID()), document);
    }

    public void deleteLibraryByOwnerId(String ownerId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("libraries");
        collection.deleteOne(Filters.eq("ownerId", ownerId));
    }
}