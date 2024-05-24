package fr.javatheque.database.repository.model;

/**
 * A class representing a person.
 */
public class Person {
    private String lastname;
    private String firstname;

    /**
     * Constructs a new Person object with the specified last name and first name.
     *
     * @param lastname  The last name of the person.
     * @param firstname The first name of the person.
     */
    public Person(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    /**
     * Gets the last name of the person.
     *
     * @return The last name of the person.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the person.
     *
     * @param lastname The last name of the person.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the first name of the person.
     *
     * @return The first name of the person.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstname The first name of the person.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}