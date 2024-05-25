package fr.javatheque.database.repository;

import fr.javatheque.database.model.Person;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;


@Stateless
public class PersonRepository {

    public Document toDocument(Person person) {
        return new Document()
                .append("lastname", person.getLastname())
                .append("firstname", person.getFirstname());
    }

    public List<Document> toDocuments(List<Person> persons) {
        return persons.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public Person toPerson(Document document) {
        String lastname = document.getString("lastname");
        String firstname = document.getString("firstname");
        return new Person(lastname, firstname);
    }

    public List<Person> toPersons(List<Document> documents) {
        return documents.stream()
                .map(this::toPerson)
                .collect(Collectors.toList());
    }
}

