package fr.javatheque.servlet;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import fr.javatheque.database.repository.FilmRepository;
import fr.javatheque.database.repository.LibraryRepository;
import fr.javatheque.database.repository.PersonRepository;
import fr.javatheque.database.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;

public abstract class AServlet extends HttpServlet {

    private static MongoDatabase mongoDatabase;

    private final FilmRepository filmRepository;
    private final LibraryRepository libraryRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public AServlet() {
        if (mongoDatabase == null) {
            synchronized (AServlet.class) {
                if (mongoDatabase == null) {
                    mongoDatabase = initializeDatabase();
                }
            }
        }
        this.filmRepository = new FilmRepository(mongoDatabase);
        this.libraryRepository = new LibraryRepository(mongoDatabase);
        this.personRepository = new PersonRepository(mongoDatabase);
        this.userRepository = new UserRepository(mongoDatabase);
    }

    private MongoDatabase initializeDatabase() {
        MongoClient mongoClient = MongoClients.create("");
        return mongoClient.getDatabase("test");
    }

    public FilmRepository getFilmRepository() {
        return filmRepository;
    }

    public LibraryRepository getLibraryRepository() {
        return libraryRepository;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}