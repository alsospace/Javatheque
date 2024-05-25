package fr.javatheque.database.model;

import java.util.List;
import java.util.UUID;

/**
 * Library represents a library entity containing a list of films owned by a specific owner.
 */
public class Library {
    private final String id ;
    private final String ownerId;
    private List<Film> films;

    public Library(String id, String ownerId, List<Film> films) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.films = films;
    }

    public Library(String ownerId, List<Film> films) {
        this(null, ownerId, films);
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}