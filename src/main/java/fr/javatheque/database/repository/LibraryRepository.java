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


@Stateless
public class LibraryRepository {

    private final MongoCollection<Document> collection;

    public LibraryRepository() {
        this.collection = MongoDBConnection.getJavathequeDatabase().getCollection("libraries");
    }


    public Library createLibrary(Library library) {
        FilmRepository fr = new FilmRepository();
        library.getFilms().forEach(fr::createFilm);
        Document document = new Document("library_id", library.getId())
                .append("owner_id", library.getOwnerId());
        collection.insertOne(document);
        return library;
    }

    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            cursor.forEachRemaining(doc -> libraries.add(documentToLibrary(doc)));
        }
        return libraries;
    }

    public Library getLibraryByOwnerId(String ownerId) {
        Document document = collection.find(Filters.eq("owner_id", ownerId)).first();
        return document != null ? documentToLibrary(document) : null;
    }

    public Library getLibraryById(String libraryId) {
        Document document = collection.find(Filters.eq("library_id", libraryId)).first();
        return document != null ? documentToLibrary(document) : null;
    }

    public void updateLibrary(Library library) {
        FilmRepository fr = new FilmRepository();
        library.getFilms().forEach(fr::createFilm);
        Document document = new Document("library_id", library.getId())
                .append("owner_id", library.getOwnerId());
        collection.replaceOne(Filters.eq("owner_id", library.getOwnerId()), document);
    }

    public void deleteLibraryByOwnerId(String ownerId) {
        collection.deleteOne(Filters.eq("owner_id", ownerId));
    }

    Library documentToLibrary(Document document) {
        FilmRepository fr = new FilmRepository();
        String libraryId = document.getString("library_id");
        String ownerId = document.getString("owner_id");
        return new Library(libraryId, ownerId, fr.getFilmsByLibraryId(libraryId));
    }
}
