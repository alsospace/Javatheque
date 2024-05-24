package fr.javatheque.json.object;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

public class Library implements Serializable {
    private String ownerId;
    private List<Film> films;

    public Library(String ownerId, List<Film> films) {
        this.ownerId = ownerId;
        this.films = films;
    }

    public String getOwner_id() {
        return ownerId;
    }

    public List<Film> getFilms() {
        return films;
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        return jsonObject;
    }

    public static Library deserializeFromJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, Library.class);
    }
}