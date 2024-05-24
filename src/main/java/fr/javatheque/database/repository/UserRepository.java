package fr.javatheque.database.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.User;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserRepository extends ARepository {
    public UserRepository() {
        super("users");
    }

    public User createUser(User user) {
        Document document = MongoUtil.objectToDocument(user);
        super.getCollection().insertOne(document);
        return user;
    }

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

    public User getUserById(String id) {
        Document document = super.getCollection().find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, User.class);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        Document document = super.getCollection().find(Filters.eq("email", email)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, User.class);
        }
        return null;
    }

    public void updateUser(User user) {
        Document document = MongoUtil.objectToDocument(user);
        super.getCollection().replaceOne(Filters.eq("id", user.getId()), document);
    }

    public void deleteUserWithID(String id) {
        super.getCollection().deleteOne(Filters.eq("id", id));
    }

    public void deleteUserWithEmail(String email) {
        super.getCollection().deleteOne(Filters.eq("email", email));
    }
}