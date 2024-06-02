package fr.javatheque.database.repository;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.MongoDBConnection;
import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Person;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class provides methods for CRUD operations on Film objects in MongoDB.
 */
@Stateless
public class FilmRepository {
    private final MongoCollection<Document> collection;
    private final PersonRepository personRepository;

    /**
     * Constructs a FilmRepository object and initializes the MongoDB collection.
     */
    public FilmRepository() {
        this.collection = MongoDBConnection.getJavathequeDatabase().getCollection("films");
        this.personRepository = new PersonRepository();
    }

    /**
     * Creates a new film in the database.
     *
     * @param film The film to create.
     * @return The created film.
     */
    public Film createFilm(Film film) {
        Document document = filmToDocument(film);
        collection.insertOne(document);
        return film;
    }

    /**
     * Retrieves all films from the database.
     *
     * @return A list of films.
     */
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            cursor.forEachRemaining(doc -> films.add(documentToFilm(doc)));
        }
        return films;
    }

    /**
     * Retrieves films by library ID from the database.
     *
     * @param libraryId The ID of the library containing the films.
     * @return A list of films in the specified library.
     */
    public List<Film> getFilmsByLibraryId(String libraryId) {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(Filters.eq("library_id", libraryId)).iterator()) {
            cursor.forEachRemaining(doc -> films.add(documentToFilm(doc)));
        }
        return films;
    }

    /**
     * Retrieves a film by its ID from the database.
     *
     * @param id The ID of the film.
     * @return An optional containing the film, if found; otherwise, empty.
     */
    public Optional<Film> getFilmById(int id) {
        Document document = collection.find(Filters.eq("film_id", id)).first();
        return document != null ? Optional.of(documentToFilm(document)) : Optional.empty();
    }

    /**
     * Updates a film in the database.
     *
     * @param film The updated film.
     */
    public void updateFilm(Film film) {
        Document document = filmToDocument(film);
        collection.replaceOne(Filters.eq("film_id", film.getId()), document);
    }

    /**
     * Deletes a film from the database by its ID.
     *
     * @param id The ID of the film to delete.
     */
    public void deleteFilm(int id) {
        collection.deleteOne(Filters.eq("film_id", id));
    }

    private Document filmToDocument(Film film) {
        return new Document("film_id", film.getId())
                .append("library_id", film.getLibraryId())
                .append("poster", film.getPoster())
                .append("lang", film.getLang())
                .append("support", film.getSupport())
                .append("title", film.getTitle())
                .append("description", film.getDescription())
                .append("releaseDate", film.getReleaseDate())
                .append("year", film.getYear())
                .append("rate", film.getRate())
                .append("opinion", film.getOpinion())
                .append("director", personRepository.toDocument(film.getDirector()))
                .append("actors", personRepository.toDocuments(film.getActors()));
    }

    private Film documentToFilm(Document document) {
        int id = document.getInteger("film_id");
        String libraryId = document.getString("library_id");
        String poster = document.getString("poster");
        String lang = document.getString("lang");
        String support = document.getString("support");
        String title = document.getString("title");
        String description = document.getString("description");
        String releaseDate = document.getString("releaseDate");
        String year = document.getString("year");
        float rate = document.getDouble("rate").floatValue();
        String opinion = document.getString("opinion");
        Person director = personRepository.toPerson((Document) document.get("director"));
        List<Person> actors = personRepository.toPersons(document.getList("actors", Document.class));
        return new Film(id, libraryId, poster, lang, support, title, description, releaseDate, year, rate, opinion, director, actors);
    }
}