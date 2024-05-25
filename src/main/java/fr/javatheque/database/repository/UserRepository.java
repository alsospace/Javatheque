package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.MongoDBConnection;
import fr.javatheque.database.model.Library;
import fr.javatheque.database.model.User;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Stateless
public class UserRepository {
    private static final String USER_ID_KEY = "user_id";
    private static final String LIBRARY_ID_KEY = "library_id";
    private static final String LASTNAME_KEY = "lastname";
    private static final String FIRSTNAME_KEY = "firstname";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private final MongoCollection<Document> collection;
    private final LibraryRepository libraryRepository;

    public UserRepository() {
        this.libraryRepository = new LibraryRepository();
        this.collection = MongoDBConnection.getJavathequeDatabase().getCollection("users");
    }


    public User createUser(User user) {
        libraryRepository.createLibrary(user.getLibrary());
        Document document = createUserDocument(user);
        collection.insertOne(document);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                users.add(documentToUser(document));
            }
        }
        return users;
    }

    public Optional<User> getUserById(String id) {
        Document document = collection.find(Filters.eq("user_id", id)).first();
        return document != null ? Optional.of(documentToUser(document)) : Optional.empty();
    }

    public Optional<User> getUserByEmail(String email) {
        Document document = collection.find(Filters.eq("email", email)).first();
        return document != null ? Optional.of(documentToUser(document)) : Optional.empty();
    }

    public void updateUser(User user) {
        Document document = createUserDocument(user);
        collection.replaceOne(Filters.eq("user_id", user.getId()), document);
    }

    public void deleteUserWithID(String id) {
        collection.deleteOne(Filters.eq("user_id", id));
    }

    public void deleteUserWithEmail(String email) {
        collection.deleteOne(Filters.eq("email", email));
    }

    private Document createUserDocument(User user) {
        return new Document()
                .append(USER_ID_KEY, user.getId())
                .append(LIBRARY_ID_KEY, user.getLibrary().getId())
                .append(LASTNAME_KEY, user.getLastname())
                .append(FIRSTNAME_KEY, user.getFirstname())
                .append(EMAIL_KEY, user.getEmail())
                .append(PASSWORD_KEY, user.getPassword());
    }

    private User documentToUser(Document document) {
        String id = document.getString(USER_ID_KEY);
        Library library = libraryRepository.getLibraryById(document.getString(LIBRARY_ID_KEY));
        String lastname = document.getString(LASTNAME_KEY);
        String firstname = document.getString(FIRSTNAME_KEY);
        String email = document.getString(EMAIL_KEY);
        String password = document.getString(PASSWORD_KEY);
        return new User(lastname, firstname, email, password, library, id, false);
    }
}