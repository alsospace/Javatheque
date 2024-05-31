package fr.javatheque.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FilmBean {
    private int id;
    private String title;
    private int year;
    private String support;
    private String lang;
    private String poster;

    public FilmBean() {
    }

    public FilmBean(int id, String title, int year, String support, String lang, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.support = support;
        this.lang = lang;
        this.poster = poster;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
