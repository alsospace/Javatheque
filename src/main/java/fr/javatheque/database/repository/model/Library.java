package fr.javatheque.database.repository.model;

import java.util.List;

/**
 * Library represents a library entity containing a list of films owned by a specific owner.
 */
public class Library {
    private String ownerId;
    private List<Film> films;

    /**
     * Constructs a new Library object with the specified owner ID and list of films.
     *
     * @param ownerId The ID of the owner of the library.
     * @param films The list of films in the library.
     */
    public Library(String ownerId, List<Film> films) {
        this.ownerId = ownerId;
        this.films = films;
    }

    /**
     * Gets the ID of the owner of the library.
     *
     * @return The ID of the owner.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the ID of the owner of the library.
     *
     * @param ownerId The ID of the owner.
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Gets the list of films in the library.
     *
     * @return The list of films.
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the list of films in the library.
     *
     * @param films The list of films.
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }
}