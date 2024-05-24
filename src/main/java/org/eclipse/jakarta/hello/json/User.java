package org.eclipse.jakarta.hello.json;

import java.io.Serializable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

class User implements Serializable {
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private Library library;
    private String id;

    public User(String lastname, String firstname, String email, String password, Library library, String id) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.library = library;
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        return jsonObject;
    }

    public static User deserializeFromJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, User.class);
    }
}
