package fr.javatheque.database.model;

import java.util.List;

/**
 * Film represents a film entity with various attributes such as ID, poster, language, support, title, description, release date,
 * year, rate, opinion, director, and a list of actors.
 */
public class Film {
    private final int id;
    private String libraryId;
    private String poster;
    private String lang;
    private String support;
    private String title;
    private String description;
    private String releaseDate;
    private String year;
    private float rate;
    private String opinion;
    private Person director;
    private List<Person> actors;

    /**
     * Constructs a new Film object with the specified properties.
     *
     * @param id           the unique identifier of the film
     * @param libraryId    the identifier of the library the film belongs to
     * @param poster       the URL or path to the film's poster image
     * @param lang         the language of the film
     * @param support      the support format of the film (e.g., DVD, Blu-ray)
     * @param title        the title of the film
     * @param description  the description or synopsis of the film
     * @param releaseDate  the release date of the film
     * @param year         the year of release of the film
     * @param rate         the user rating of the film
     * @param opinion      the user's opinion or review of the film
     * @param director     the director of the film
     * @param actors       the list of actors in the film
     */
    public Film(int id, String libraryId, String poster, String lang, String support, String title, String description,
                String releaseDate, String year, float rate, String opinion, Person director, List<Person> actors) {
        this.id = id;
        this.libraryId = libraryId;
        this.poster = poster;
        this.lang = lang;
        this.support = support;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.year = year;
        this.rate = rate;
        this.opinion = opinion;
        this.director = director;
        this.actors = actors;
    }

    /**
     * Returns the unique identifier of the film.
     *
     * @return the film's identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the identifier of the library the film belongs to.
     *
     * @return the library identifier
     */
    public String getLibraryId() {
        return libraryId;
    }

    /**
     * Sets the identifier of the library the film belongs to.
     *
     * @param libraryId the library identifier to set
     */
    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    /**
     * Returns the URL or path to the film's poster image.
     *
     * @return the film's poster image URL or path
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Sets the URL or path to the film's poster image.
     *
     * @param poster the poster image URL or path to set
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Returns the language of the film.
     *
     * @return the film's language
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the language of the film.
     *
     * @param lang the language to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Returns the support format of the film (e.g., DVD, Blu-ray).
     *
     * @return the film's support format
     */
    public String getSupport() {
        return support;
    }

    /**
     * Sets the support format of the film (e.g., DVD, Blu-ray).
     *
     * @param support the support format to set
     */
    public void setSupport(String support) {
        this.support = support;
    }

    /**
     * Returns the title of the film.
     *
     * @return the film's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description or synopsis of the film.
     *
     * @return the film's description or synopsis
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or synopsis of the film.
     *
     * @param description the description or synopsis to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the release date of the film.
     *
     * @return the film's release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the film.
     *
     * @param releaseDate the release date to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the year of release of the film.
     *
     * @return the film's year of release
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year of release of the film.
     *
     * @param year the year of release to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Returns the user rating of the film.
     *
     * @return the film's user rating
     */
    public float getRate() {
        return rate;
    }

    /**
     * Sets the user rating of the film.
     *
     * @param rate the user rating to set
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Returns the user's opinion or review of the film.
     *
     * @return the user's opinion or review
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * Sets the user's opinion or review of the film.
     *
     * @param opinion the opinion or review to set
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * Returns the director of the film.
     *
     * @return the film's director
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Sets the director of the film.
     *
     * @param director the director to set
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * Returns the list of actors in the film.
     *
     * @return the film's list of actors
     */
    public List<Person> getActors() {
        return actors;
    }

    /**
     * Sets the list of actors in the film.
     *
     * @param actors the list of actors to set
     */
    public void setActors(List<Person> actors) {
        this.actors = actors;
    }
}