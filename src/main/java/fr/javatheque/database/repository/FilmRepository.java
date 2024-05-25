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


@Stateless
public class FilmRepository {
    private final MongoCollection<Document> collection;
    private final PersonRepository personRepository;

    public FilmRepository() {
        this.collection = MongoDBConnection.getJavathequeDatabase().getCollection("films");
        this.personRepository = new PersonRepository();
    }

    public Film createFilm(Film film) {
        Document document = filmToDocument(film);
        collection.insertOne(document);
        return film;
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            cursor.forEachRemaining(doc -> films.add(documentToFilm(doc)));
        }
        return films;
    }

    public List<Film> getFilmsByLibraryId(String libraryId) {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find(Filters.eq("library_id", libraryId)).iterator()) {
            cursor.forEachRemaining(doc -> films.add(documentToFilm(doc)));
        }
        return films;
    }

    public Optional<Film> getFilmById(int id) {
        Document document = collection.find(Filters.eq("film_id", id)).first();
        return document != null ? Optional.of(documentToFilm(document)) : Optional.empty();
    }

    public void updateFilm(Film film) {
        Document document = filmToDocument(film);
        collection.replaceOne(Filters.eq("film_id", film.getId()), document);
    }

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
        int year = document.getInteger("year");
        float rate = document.getDouble("rate").floatValue();
        String opinion = document.getString("opinion");
        Person director = personRepository.toPerson((Document) document.get("director"));
        List<Person> actors = personRepository.toPersons(document.getList("actors", Document.class));
        return new Film(id, libraryId, poster, lang, support, title, description, releaseDate, year, rate, opinion, director, actors);
    }
}