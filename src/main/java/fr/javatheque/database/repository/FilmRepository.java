package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Film;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * FilmRepository is a stateless class representing a repository for managing Film entities in MongoDB.
 */
@Stateless
public class FilmRepository extends ARepository {

    /**
     * Constructs a new FilmRepository instance.
     * Initializes the repository with the "films" collection.
     */
    public FilmRepository() {
        super("films");
    }

    /**
     * Creates a new film in the repository.
     *
     * @param film The film to be created.
     * @return The created film.
     */
    public Film createFilm(Film film) {
        Document document = MongoUtil.objectToDocument(film);
        super.getCollection().insertOne(document);
        return film;
    }

    /**
     * Retrieves all films from the repository.
     *
     * @return A list of all films.
     */
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try (MongoCursor<Document> cursor = super.getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                films.add(MongoUtil.documentToObject(document, Film.class));
            }
        }
        return films;
    }

    /**
     * Retrieves a film by its ID from the repository.
     *
     * @param id The ID of the film to retrieve.
     * @return The film with the specified ID, or null if not found.
     */
    public Film getFilmById(int id) {
        Document document = super.getCollection().find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Film.class);
        }
        return null;
    }

    /**
     * Updates a film in the repository.
     *
     * @param film The film to update.
     */
    public void updateFilm(Film film) {
        Document document = MongoUtil.objectToDocument(film);
        super.getCollection().replaceOne(Filters.eq("id", film.getId()), document);
    }

    /**
     * Deletes a film from the repository by its ID.
     *
     * @param id The ID of the film to delete.
     */
    public void deleteFilm(int id) {
        super.getCollection().deleteOne(Filters.eq("id", id));
    }
}
