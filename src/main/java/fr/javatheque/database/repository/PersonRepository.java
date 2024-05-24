package fr.javatheque.database.repository;

import fr.javatheque.database.repository.model.Person;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * PersonRepository is a stateless class representing a repository for managing Person entities in MongoDB.
 */
@Stateless
public class PersonRepository extends ARepository {

    /**
     * Constructs a new PersonRepository instance.
     * Initializes the repository with the "person" collection.
     */
    public PersonRepository() {
        super("person");
    }

    /**
     * Converts a Person object to a MongoDB Document.
     *
     * @param person The Person object to convert.
     * @return The corresponding MongoDB Document.
     */
    public static Document documentFromPerson(Person person) {
        return new Document()
                .append("lastname", person.getLastname())
                .append("firstname", person.getFirstname());
    }

    /**
     * Converts a list of Person objects to a list of MongoDB Documents.
     *
     * @param persons The list of Person objects to convert.
     * @return The corresponding list of MongoDB Documents.
     */
    public static List<Document> documentsFromPersons(List<Person> persons) {
        List<Document> documents = new ArrayList<>();
        for (Person person : persons) {
            documents.add(documentFromPerson(person));
        }
        return documents;
    }

    /**
     * Converts a MongoDB Document to a Person object.
     *
     * @param document The MongoDB Document to convert.
     * @return The corresponding Person object.
     */
    public static Person documentToPerson(Document document) {
        String lastname = document.getString("lastname");
        String firstname = document.getString("firstname");
        return new Person(lastname, firstname);
    }

    /**
     * Converts a list of MongoDB Documents to a list of Person objects.
     *
     * @param documents The list of MongoDB Documents to convert.
     * @return The corresponding list of Person objects.
     */
    public static List<Person> documentsToPersons(List<Document> documents) {
        List<Person> persons = new ArrayList<>();
        for (Document document : documents) {
            persons.add(documentToPerson(document));
        }
        return persons;
    }
}

