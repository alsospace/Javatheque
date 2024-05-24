package fr.javatheque.database.repository.model;

import java.util.List;

public class Library{
    private String ownerId;
    private List<Film> films;

    public Library(String ownerId, List<Film> films) {
        this.ownerId = ownerId;
        this.films = films;
    }

    public String getOwnerID() {
        return ownerId;
    }

    public List<Film> getFilms() {
        return films;
    }

}