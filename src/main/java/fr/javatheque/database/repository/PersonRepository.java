package fr.javatheque.database.repository;

import fr.javatheque.database.model.Person;
import jakarta.ejb.Stateless;
import org.bson.BsonWriter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * PersonRepository is a stateless class representing a repository for managing Person entities in MongoDB.
 */
@Stateless
public class PersonRepository {

    public static Document documentFromPerson(Person person) {
        return new Document()
                .append("lastname", person.getLastname())
                .append("firstname", person.getFirstname());
    }

    public static List<Document> documentsFromPersons(List<Person> persons) {
        List<Document> documents = new ArrayList<>();
        for (Person person : persons) {
            documents.add(documentFromPerson(person));
        }
        return documents;
    }

    public static Person documentToPerson(Document document) {
        String lastname = document.getString("lastname");
        String firstname = document.getString("firstname");
        return new Person(lastname, firstname);
    }

    public static List<Person> documentsToPersons(List<Document> documents) {
        List<Person> persons = new ArrayList<>();
        for (Document document : documents) {
            persons.add(documentToPerson(document));
        }
        return persons;
    }
}

