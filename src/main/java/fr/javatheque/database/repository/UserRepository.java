package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.MongoDBConnection;
import fr.javatheque.database.model.Library;
import fr.javatheque.database.model.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


@Stateless
public class UserRepository {

    private final MongoCollection<Document> collection;
    private final LibraryRepository libraryRepository;

    public UserRepository() {
        this.libraryRepository = new LibraryRepository();
        MongoDatabase mongoDatabase = MongoDBConnection.getJavathequeDatabase();
        this.collection = mongoDatabase.getCollection("users");
    }

    public User createUser(User user) {
        libraryRepository.createLibrary(user.getLibrary());
        Document document = new Document()
                .append("user_id", user.getId())
                .append("library_id", user.getLibrary().getId())
                .append("lastname", user.getLastname())
                .append("firstname", user.getFirstname())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        this.collection.insertOne(document);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (MongoCursor<Document> cursor = this.collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                users.add(documentToUser(document));
            }
        }
        return users;
    }

    public User getUserById(String id) {
        Document document = this.collection.find(Filters.eq("user_id", id)).first();
        if (document != null) {
            return documentToUser(document);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        Document document = this.collection.find(Filters.eq("email", email)).first();
        if (document != null) {
            return documentToUser(document);
        }
        return null;
    }

    public void updateUser(User user) {
        Document document = new Document()
                .append("user_id", user.getId())
                .append("library_id", user.getLibrary().getId())
                .append("lastname", user.getLastname())
                .append("firstname", user.getFirstname())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        this.collection.replaceOne(Filters.eq("user_id", user.getId()), document);
    }

    public void deleteUserWithID(String id) {
        this.collection.deleteOne(Filters.eq("user_id", id));
    }

    public void deleteUserWithEmail(String email) {
        this.collection.deleteOne(Filters.eq("email", email));
    }

    private User documentToUser(Document document) {
        String id = document.getString("user_id");
        Library library = libraryRepository.documentToLibrary((Document) document.get("library_id"));
        String lastname = document.getString("lastname");
        String firstname = document.getString("firstname");
        String email = document.getString("email");
        String password = document.getString("password");

        return new User(lastname, firstname, email, password, library, id, false);
    }
}
