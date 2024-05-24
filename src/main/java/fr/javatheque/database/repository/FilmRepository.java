package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Film;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class FilmRepository extends ARepository {
    public FilmRepository() {
        super("films");
    }

    public Film createFilm(Film film) {
        Document document = MongoUtil.objectToDocument(film);
        super.getCollection().insertOne(document);
        return film;
    }

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

    public Film getFilmById(int id) {
        Document document = super.getCollection().find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Film.class);
        }
        return null;
    }

    public void updateFilm(Film film) {
        Document document = MongoUtil.objectToDocument(film);
        super.getCollection().replaceOne(Filters.eq("id", film.getId()), document);
    }

    public void deleteFilm(int id) {
        super.getCollection().deleteOne(Filters.eq("id", id));
    }
}
