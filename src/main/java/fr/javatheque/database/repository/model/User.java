package fr.javatheque.database.repository.model;

import fr.javatheque.util.PasswordUtil;

import java.util.*;

/**
 * A class representing a user.
 */
public class User {

    private transient final List<User> users = new ArrayList<>();
    private transient final Map<String, User> usersByEmail = new HashMap<>();

    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private Library library;
    private String id;

    /**
     * Constructs a new User object with the specified details.
     *
     * @param lastname   The last name of the user.
     * @param firstname  The first name of the user.
     * @param email      The email of the user.
     * @param password   The password of the user.
     * @param library    The library associated with the user.
     * @param id         The ID of the user.
     * @param needToEncryptPassword  True if the password needs to be encrypted, false otherwise.
     */
    public User(String lastname, String firstname, String email, String password, Library library, String id,
                boolean needToEncryptPassword) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = needToEncryptPassword ? PasswordUtil.encryptPassword(password) : password;
        this.library = library != null ? library : new Library(getId(), new ArrayList<>());
        this.id = id != null ? id : UUID.randomUUID().toString();
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastname The last name of the user.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstname The first name of the user.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the library associated with the user.
     *
     * @return The library associated with the user.
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with the user.
     *
     * @param library The library associated with the user.
     */
    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID of the user.
     */
    public void setId(String id) {
        this.id = id;
    }
}