package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.MongoDBConnection;
import fr.javatheque.database.model.Library;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides methods for CRUD operations on Library objects in MongoDB.
 */
@Stateless
public class LibraryRepository {

    private final MongoCollection<Document> collection;

    /**
     * Constructs a LibraryRepository object and initializes the MongoDB collection.
     */
    public LibraryRepository() {
        this.collection = MongoDBConnection.getJavathequeDatabase().getCollection("libraries");
    }

    /**
     * Creates a new library in the database.
     *
     * @param library The library to create.
     * @return The created library.
     */
    public Library createLibrary(Library library) {
        FilmRepository fr = new FilmRepository();
        library.getFilms().forEach(fr::createFilm);
        Document document = new Document("library_id", library.getId())
                .append("owner_id", library.getOwnerId());
        collection.insertOne(document);
        return library;
    }

    /**
     * Retrieves all libraries from the database.
     *
     * @return A list of libraries.
     */
    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            cursor.forEachRemaining(doc -> libraries.add(documentToLibrary(doc)));
        }
        return libraries;
    }

    /**
     * Retrieves a library by its owner's ID from the database.
     *
     * @param ownerId The ID of the library's owner.
     * @return The library, if found; otherwise, null.
     */
    public Library getLibraryByOwnerId(String ownerId) {
        Document document = collection.find(Filters.eq("owner_id", ownerId)).first();
        return document != null ? documentToLibrary(document) : null;
    }

    /**
     * Retrieves a library by its ID from the database.
     *
     * @param libraryId The ID of the library.
     * @return The library, if found; otherwise, null.
     */
    public Library getLibraryById(String libraryId) {
        Document document = collection.find(Filters.eq("library_id", libraryId)).first();
        return document != null ? documentToLibrary(document) : null;
    }

    /**
     * Updates a library in the database.
     *
     * @param library The updated library.
     */
    public void updateLibrary(Library library) {
        FilmRepository fr = new FilmRepository();
        library.getFilms().forEach(fr::createFilm);
        Document document = new Document("library_id", library.getId())
                .append("owner_id", library.getOwnerId());
        collection.replaceOne(Filters.eq("owner_id", library.getOwnerId()), document);
    }

    /**
     * Deletes a library by its owner's ID from the database.
     *
     * @param ownerId The ID of the library's owner.
     */
    public void deleteLibraryByOwnerId(String ownerId) {
        collection.deleteOne(Filters.eq("owner_id", ownerId));
    }

    /**
     * Converts a Document to a Library object.
     *
     * @param document The Document to convert.
     * @return The Library object.
     */
    Library documentToLibrary(Document document) {
        FilmRepository fr = new FilmRepository();
        String libraryId = document.getString("library_id");
        String ownerId = document.getString("owner_id");
        return new Library(libraryId, ownerId, fr.getFilmsByLibraryId(libraryId));
    }
}