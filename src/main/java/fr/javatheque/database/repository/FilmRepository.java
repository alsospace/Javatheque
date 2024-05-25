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

/**
 * FilmRepository is a stateless class representing a repository for managing Film entities in MongoDB.
 */
@Stateless
public class FilmRepository {
    private final MongoCollection<Document> collection;

    public FilmRepository() {
        MongoDatabase mongoDatabase = MongoDBConnection.getJavathequeDatabase();
        this.collection = mongoDatabase.getCollection("films");
    }

    public Film createFilm(Film film) {
        Document document = new Document()
                .append("film_id", film.getId())
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
                .append("director", PersonRepository.documentFromPerson(film.getDirector()))
                .append("actors", PersonRepository.documentsFromPersons(film.getActors()));
        this.collection.insertOne(document);
        return film;
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = this.collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                films.add(documentToFilm(document));
            }
        }
        return films;
    }

    public List<Film> getFilmsByLibraryId(String libraryId) {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = this.collection.find(Filters.eq("library_id", libraryId)).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                films.add(documentToFilm(document));
            }
        }
        return films;
    }

    public Film getFilmById(int id) {
        Document document = this.collection.find(Filters.eq("film_id", id)).first();
        if (document != null) {
            return documentToFilm(document);
        }
        return null;
    }

    public void updateFilm(Film film) {
        Document document = new Document()
                .append("film_id", film.getId())
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
                .append("director", PersonRepository.documentFromPerson(film.getDirector()))
                .append("actors", PersonRepository.documentsFromPersons(film.getActors()));
        this.collection.replaceOne(Filters.eq("film_id", film.getId()), document);
    }

    public void deleteFilm(int id) {
        this.collection.deleteOne(Filters.eq("film_id", id));
    }

    private Film documentToFilm(Document document) {
        int id = document.getInteger("film_id");
        String libraryId = document.getString("libraryId");
        String poster = document.getString("poster");
        String lang = document.getString("lang");
        String support = document.getString("support");
        String title = document.getString("title");
        String description = document.getString("description");
        String releaseDate = document.getString("releaseDate");
        int year = document.getInteger("year");
        float rate = document.getDouble("rate").floatValue();
        String opinion = document.getString("opinion");
        Person director = PersonRepository.documentToPerson((Document) document.get("director"));
        List<Person> actors = PersonRepository.documentsToPersons(document.getList("actors", Document.class));
        return new Film(id, libraryId, poster, lang, support, title, description, releaseDate, year, rate, opinion, director, actors);
    }
}