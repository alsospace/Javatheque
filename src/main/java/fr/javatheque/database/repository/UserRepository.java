package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.User;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository is a stateless class representing a repository for managing User entities in MongoDB.
 */
@Stateless
public class UserRepository extends ARepository {

    /**
     * Constructs a new UserRepository instance.
     * Initializes the repository with the "users" collection.
     */
    public UserRepository() {
        super("users");
    }

    /**
     * Creates a new user in the repository.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    public User createUser(User user) {
        Document document = MongoUtil.objectToDocument(user);
        super.getCollection().insertOne(document);
        return user;
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (MongoCursor<Document> cursor = super.getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                users.add(MongoUtil.documentToObject(document, User.class));
            }
        }
        return users;
    }

    /**
     * Retrieves a user by their ID from the repository.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID, or null if not found.
     */
    public User getUserById(String id) {
        Document document = super.getCollection().find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, User.class);
        }
        return null;
    }

    /**
     * Retrieves a user by their email from the repository.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the specified email, or null if not found.
     */
    public User getUserByEmail(String email) {
        Document document = super.getCollection().find(Filters.eq("email", email)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, User.class);
        }
        return null;
    }

    /**
     * Updates a user in the repository.
     *
     * @param user The user to update.
     */
    public void updateUser(User user) {
        Document document = MongoUtil.objectToDocument(user);
        super.getCollection().replaceOne(Filters.eq("id", user.getId()), document);
    }

    /**
     * Deletes a user from the repository by their ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUserWithID(String id) {
        super.getCollection().deleteOne(Filters.eq("id", id));
    }

    /**
     * Deletes a user from the repository by their email.
     *
     * @param email The email of the user to delete.
     */
    public void deleteUserWithEmail(String email) {
        super.getCollection().deleteOne(Filters.eq("email", email));
    }
}