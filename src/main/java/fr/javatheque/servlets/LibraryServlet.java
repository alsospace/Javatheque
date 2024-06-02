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

/**
 * Servlet that handles requests related to the user's library.
 */
@WebServlet(name = "LibraryServlet", urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {
    /**
     * Handles the HTTP GET request for the user's library.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
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

    /**
     * Searches for films in the user's library based on the provided search query.
     *
     * @param library     the user's library
     * @param searchQuery the search query
     * @return the list of films matching the search query
     */
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