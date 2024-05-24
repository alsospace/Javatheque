package fr.javatheque.database.repository.model;

import java.util.List;

/**
 * Film represents a film entity with various attributes such as ID, poster, language, support, title, description, release date,
 * year, rate, opinion, director, and a list of actors.
 */
public class Film{
    private int id;
    private String poster;
    private String lang;
    private String support;
    private String title;
    private String description;
    private String releaseDate;
    private int year;
    private float rate;
    private String opinion;
    private Person director;
    private List<Person> actors;

    /**
     * Constructs a new Film object with the specified attributes.
     *
     * @param id           The ID of the film.
     * @param poster       The URL of the film poster.
     * @param lang         The language of the film.
     * @param support      The support type of the film (e.g., DVD, Blu-ray).
     * @param title        The title of the film.
     * @param description  The description of the film.
     * @param releaseDate  The release date of the film.
     * @param year         The year of release of the film.
     * @param rate         The rating of the film.
     * @param opinion      The personal opinion on the film.
     * @param director     The director of the film.
     * @param actors       The list of actors in the film.
     */
    public Film(int id, String poster, String lang, String support, String title, String description, String releaseDate, int year, float rate, String opinion, Person director, List<Person> actors) {
        this.id = id;
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
     * Retrieves the ID of the film.
     *
     * @return The ID of the film.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the film.
     *
     * @param id The ID of the film.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the URL of the film poster.
     *
     * @return The URL of the film poster.
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Sets the URL of the film poster.
     *
     * @param poster The URL of the film poster.
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Retrieves the language of the film.
     *
     * @return The language of the film.
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the language of the film.
     *
     * @param lang The language of the film.
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Retrieves the support type of the film.
     *
     * @return The support type of the film.
     */
    public String getSupport() {
        return support;
    }

    /**
     * Sets the support type of the film.
     *
     * @param support The support type of the film.
     */
    public void setSupport(String support) {
        this.support = support;
    }

    /**
     * Retrieves the title of the film.
     *
     * @return The title of the film.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the film.
     *
     * @param title The title of the film.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the film.
     *
     * @return The description of the film.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the film.
     *
     * @param description The description of the film.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the release date of the film.
     *
     * @return The release date of the film.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the film.
     *
     * @param releaseDate The release date of the film.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Retrieves the year of release of the film.
     *
     * @return The year of release of the film.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of release of the film.
     *
     * @param year The year of release of the film.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retrieves the rating of the film.
     *
     * @return The rating of the film.
     */
    public float getRate() {
        return rate;
    }

    /**
     * Sets the rating of the film.
     *
     * @param rate The rating of the film.
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Retrieves the personal opinion on the film.
     *
     * @return The personal opinion on the film.
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * Sets the personal opinion on the film.
     *
     * @param opinion The personal opinion on the film.
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * Retrieves the director of the film.
     *
     * @return The director of the film.
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Sets the director of the film.
     *
     * @param director The director of the film.
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * Retrieves the list of actors in the film.
     *
     * @return The list of actors in the film.
     */
    public List<Person> getActors() {
        return actors;
    }

    /**
     * Sets the list of actors in the film.
     *
     * @param actors The list of actors in the film.
     */
    public void setActors(List<Person> actors) {
        this.actors = actors;
    }
}
