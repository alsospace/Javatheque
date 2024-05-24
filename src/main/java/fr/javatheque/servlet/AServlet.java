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
    private final FilmRepository filmRepository;
    private final LibraryRepository libraryRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public AServlet() {
        this.filmRepository = new FilmRepository();
        this.libraryRepository = new LibraryRepository();
        this.personRepository = new PersonRepository();
        this.userRepository = new UserRepository();
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