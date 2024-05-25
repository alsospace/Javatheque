package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.MongoDBConnection;
import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Library;
import fr.javatheque.database.model.Person;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryRepository is a stateless class representing a repository for managing Library entities in MongoDB.
 */
@Stateless
public class LibraryRepository {
    private final MongoCollection<Document> collection;

    public LibraryRepository() {
        MongoDatabase mongoDatabase = MongoDBConnection.getJavathequeDatabase();
        this.collection = mongoDatabase.getCollection("libraries");
    }

    public Library createLibrary(Library library) {
        List<Document> filmDocuments = new ArrayList<>();
        for (Film film : library.getFilms()) {
            Document filmDocument = new Document()
                    .append("library_id", film.getId())
                    .append("poster", film.getPoster())
                    .append("lang", film.getLang())
                    .append("support", film.getSupport())
                    .append("title", film.getTitle())
                    .append("description", film.getDescription())
                    .append("releaseDate", film.getReleaseDate())
                    .append("year", film.getYear())
                    .append("rate", film.getRate())
                    .append("opinion", film.getOpinion())
                    .append("director", PersonRepository.documentFromPerson(film.getDirector()))
                    .append("actors", PersonRepository.documentsFromPersons(film.getActors()));
            filmDocuments.add(filmDocument);
        }
        Document document = new Document()
                .append("library_id", library.getId())
                .append("owner_id", library.getOwnerId())
                .append("films", filmDocuments);
        this.collection.insertOne(document);
        return library;
    }


    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        try (MongoCursor<Document> cursor = this.collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                libraries.add(documentToLibrary(document));
            }
        }
        return libraries;
    }

    public Library getLibraryByOwnerId(String ownerId) {
        Document document = this.collection.find(Filters.eq("owner_id", ownerId)).first();
        if (document != null) {
            return documentToLibrary(document);
        }
        return null;
    }

    public Library getLibraryById(String libraryId) {
        Document document = this.collection.find(Filters.eq("library_id", libraryId)).first();
        if (document != null) {
            return documentToLibrary(document);
        }
        return null;
    }

    public void updateLibrary(Library library) {
        List<Document> filmDocuments = new ArrayList<>();
        for (Film film : library.getFilms()) {
            Document filmDocument = new Document()
                    .append("id", film.getId())
                    .append("poster", film.getPoster())
                    .append("lang", film.getLang())
                    .append("support", film.getSupport())
                    .append("title", film.getTitle())
                    .append("description", film.getDescription())
                    .append("releaseDate", film.getReleaseDate())
                    .append("year", film.getYear())
                    .append("rate", film.getRate())
                    .append("opinion", film.getOpinion())
                    .append("director", PersonRepository.documentFromPerson(film.getDirector()))
                    .append("actors", PersonRepository.documentsFromPersons(film.getActors()));
            filmDocuments.add(filmDocument);
        }
        Document document = new Document()
                .append("library_id", library.getId())
                .append("owner_id", library.getOwnerId())
                .append("films", filmDocuments);
        this.collection.replaceOne(Filters.eq("owner_id", library.getOwnerId()), document);
    }

    public void deleteLibraryByOwnerId(String ownerId) {
        this.collection.deleteOne(Filters.eq("owner_id", ownerId));
    }

    Library documentToLibrary(Document document) {
        String libraryId = document.getString("library_id");
        String ownerId = document.getString("owner_id");
        List<Film> films = new ArrayList<>();
        List<Document> filmDocuments = document.getList("films", Document.class);
        for (Document filmDocument : filmDocuments) {
            int id = filmDocument.getInteger("id");
            String poster = filmDocument.getString("poster");
            String lang = filmDocument.getString("lang");
            String support = filmDocument.getString("support");
            String title = filmDocument.getString("title");
            String description = filmDocument.getString("description");
            String releaseDate = filmDocument.getString("releaseDate");
            int year = filmDocument.getInteger("year");
            float rate = filmDocument.getDouble("rate").floatValue();
            String opinion = filmDocument.getString("opinion");
            Person director = PersonRepository.documentToPerson((Document) filmDocument.get("director"));
            List<Person> actors = PersonRepository.documentsToPersons(filmDocument.getList("actors", Document.class));
            Film film = new Film(id, libraryId,  poster, lang, support, title, description, releaseDate, year, rate, opinion, director, actors);
            films.add(film);
        }
        return new Library(libraryId, ownerId, films);
    }
}
