package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Library;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryRepository is a stateless class representing a repository for managing Library entities in MongoDB.
 */
@Stateless
public class LibraryRepository extends ARepository {

    /**
     * Constructs a new LibraryRepository instance.
     * Initializes the repository with the "libraries" collection.
     */
    public LibraryRepository() {
        super("libraries");
    }

    /**
     * Creates a new library in the repository.
     *
     * @param library The library to be created.
     * @return The created library.
     */
    public Library createLibrary(Library library) {
        Document document = MongoUtil.objectToDocument(library);
        super.getCollection().insertOne(document);
        return library;
    }

    /**
     * Retrieves all libraries from the repository.
     *
     * @return A list of all libraries.
     */
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

    /**
     * Retrieves a library by its owner ID from the repository.
     *
     * @param ownerId The ID of the owner of the library.
     * @return The library with the specified owner ID, or null if not found.
     */
    public Library getLibraryByOwnerId(String ownerId) {
        Document document = super.getCollection().find(Filters.eq("ownerId", ownerId)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Library.class);
        }
        return null;
    }

    /**
     * Updates a library in the repository.
     *
     * @param library The library to update.
     */
    public void updateLibrary(Library library) {
        Document document = MongoUtil.objectToDocument(library);
        super.getCollection().replaceOne(Filters.eq("ownerId", library.getOwnerId()), document);
    }

    /**
     * Deletes a library from the repository by its owner ID.
     *
     * @param ownerId The ID of the owner of the library to delete.
     */
    public void deleteLibraryByOwnerId(String ownerId) {
        super.getCollection().deleteOne(Filters.eq("ownerId", ownerId));
    }
}
