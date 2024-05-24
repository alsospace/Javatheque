package fr.javatheque.database.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.javatheque.database.repository.model.Library;
import fr.javatheque.database.repository.model.User;
import fr.javatheque.util.MongoUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserRepository {

    private final MongoDatabase mongoDatabase;

    public UserRepository(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }
    public User createUser(User user) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = MongoUtil.objectToDocument(user);
        collection.insertOne(document);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                users.add(MongoUtil.documentToObject(document, User.class));
            }
        } finally {
            cursor.close();
        }
        return users;
    }

    public User getUserById(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = collection.find(Filters.eq("id", id)).first();
        if (document != null) {
            return MongoUtil.documentToObject(document, User.class);
        }
        return null;
    }

    public void updateUser(User user) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = MongoUtil.objectToDocument(user);
        collection.replaceOne(Filters.eq("id", user.getId()), document);
    }

    public void deleteUser(String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        collection.deleteOne(Filters.eq("id", id));
    }
}