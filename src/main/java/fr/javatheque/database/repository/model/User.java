package fr.javatheque.database.repository.model;

import fr.javatheque.util.PasswordUtil;

import java.util.*;

public class User {

    private transient final List<User> users = new ArrayList<>();
    private transient final Map<String, User> usersByEmail = new HashMap<>();

    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private Library library;
    private String id;

    public User(String lastname, String firstname, String email, String password, Library library, String id,
                boolean needToEncryptPassword) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = needToEncryptPassword ? PasswordUtil.encryptPassword(password) : password;
        this.library = library != null ? library : new Library(getId(), new ArrayList<>());
        this.id = id != null ? id : UUID.randomUUID().toString();
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
}
