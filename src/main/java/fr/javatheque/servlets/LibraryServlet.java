package fr.javatheque.servlets;

import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Library;
import fr.javatheque.database.model.User;
import fr.javatheque.database.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "LibraryServlet", urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = (String) request.getSession().getAttribute("userID");

        UserRepository ur = new UserRepository();
        Optional<User> target = ur.getUserById(userID);

        if (target.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = target.get();

        String searchQuery = request.getParameter("search");
        Library library = user.getLibrary();

        List<Film> films;
        if (searchQuery != null && searchQuery.equalsIgnoreCase("all")) {
            films = library.getFilms();
        } else {
            films = searchFilmsInLibrary(library, searchQuery);
        }

        request.setAttribute("films", films);
        request.getRequestDispatcher("/views/library.jsp").forward(request, response);
    }

    private List<Film> searchFilmsInLibrary(Library library, String searchQuery) {
        List<Film> allFilms = library.getFilms();
        if (searchQuery == null || searchQuery.isEmpty()) {
            return allFilms;
        }
        return allFilms.stream()
                .filter(film -> film.getTitle().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());
    }
}
