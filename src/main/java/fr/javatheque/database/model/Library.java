package fr.javatheque.database.model;

import java.util.List;
import java.util.UUID;

/**
 * Library represents a library entity containing a list of films owned by a specific owner.
 */
/**
 * Represents a library containing films.
 */
public class Library {
    private final String id ;
    private final String ownerId;
    private List<Film> films;

    /**
     * Constructs a Library object with the specified ID, owner ID, and list of films.
     *
     * @param id The ID of the library.
     * @param ownerId The ID of the library's owner.
     * @param films The list of films in the library.
     */
    public Library(String id, String ownerId, List<Film> films) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.films = films;
    }

    /**
     * Constructs a Library object with the specified owner ID and list of films.
     * The ID is generated automatically.
     *
     * @param ownerId The ID of the library's owner.
     * @param films The list of films in the library.
     */
    public Library(String ownerId, List<Film> films) {
        this(null, ownerId, films);
    }

    /**
     * Gets the ID of the library.
     *
     * @return The ID of the library.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the ID of the library's owner.
     *
     * @return The ID of the library's owner.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Gets the list of films in the library.
     *
     * @return The list of films in the library.
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the library.
     *
     * @param films The list of films to set.
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}