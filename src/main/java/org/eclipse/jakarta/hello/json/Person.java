package org.eclipse.jakarta.hello.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class Person implements Serializable {
    private String lastname;
    private String firstname;

    public Person(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
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

    public JsonObject toJson() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        return jsonObject;
    }

    public static Person deserializeFromJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, Person.class);
    }
}