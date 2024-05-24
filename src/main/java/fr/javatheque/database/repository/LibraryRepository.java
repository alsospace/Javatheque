package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Library;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class LibraryRepository extends ARepository {
    public LibraryRepository() {
        super("libraries");
    }

    public Library createLibrary(Library library) {
        Document document = MongoUtil.objectToDocument(library);
        super.getCollection().insertOne(document);
        return library;
    }

    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        try (MongoCursor<Document> cursor = super.getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                libraries.add(MongoUtil.documentToObject(document, Library.class));
            }
        }
        return libraries;
    }

    public Library getLibraryByOwnerId(String ownerId) {
        Document document = super.getCollection().find(Filters.eq("ownerId", ownerId)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Library.class);
        }
        return null;
    }

    public void updateLibrary(Library library) {
        Document document = MongoUtil.objectToDocument(library);
        super.getCollection().replaceOne(Filters.eq("ownerId", library.getOwnerID()), document);
    }

    public void deleteLibraryByOwnerId(String ownerId) {
        super.getCollection().deleteOne(Filters.eq("ownerId", ownerId));
    }
}