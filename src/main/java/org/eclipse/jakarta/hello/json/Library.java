package org.eclipse.jakarta.hello.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

class Library implements Serializable {
    private String owner_id;
    private List<Film> films;

    public Library(String owner_id, List<Film> films) {
        this.owner_id = owner_id;
        this.films = films;
    }

    public String getOwner_id() {
        return owner_id;
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