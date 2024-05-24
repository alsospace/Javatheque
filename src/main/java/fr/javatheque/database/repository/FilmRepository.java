package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Film;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FilmRepository {
    private final MongoDatabase mongoDatabase;

    public FilmRepository(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public Film createFilm(Film film) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("films");
        Document document = MongoUtil.objectToDocument(film);
        collection.insertOne(document);
        return film;
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection("films");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                films.add(MongoUtil.documentToObject(document, Film.class));
            }
        } finally {
            cursor.close();
        }
        return films;
    }

    public Film getFilmById(int id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("films");
        Document document = collection.find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, Film.class);
        }
        return null;
    }

    public void updateFilm(Film film) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("films");
        Document document = MongoUtil.objectToDocument(film);
        collection.replaceOne(Filters.eq("id", film.getId()), document);
    }

    public void deleteFilm(int id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("films");
        collection.deleteOne(Filters.eq("id", id));
    }
}
