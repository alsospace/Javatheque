package org.eclipse.jakarta.hello.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

class Film implements Serializable {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        return jsonObject;
    }

    public static Film deserializeFromJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, Film.class);
    }
}