package fr.javatheque.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.javatheque.beans.ErrorMessageBean;
import fr.javatheque.beans.SuccessMessageBean;
import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.User;
import fr.javatheque.database.repository.FilmRepository;
import fr.javatheque.database.repository.UserRepository;
import fr.javatheque.util.TMDBRequest;
import fr.javatheque.util.TMDBUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * test for pipeline
 * again again again
 * 4eme test
 * test for pipeline encore car par d'iédee
 * Servlet that handles requests related to films.
 */
@WebServlet(name = "FilmServlet", urlPatterns = {"/film/*"})
public class FilmServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Inject
    private SuccessMessageBean successMessageBean;

    /**
     * Handles the HTTP GET request for film-related actions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);
        String userID = (String) request.getSession().getAttribute("userID");
        Optional<User> target = getUserFromDatabase(userID);

        if (target.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        switch (action.toLowerCase()) {
            case "/film/search" -> request.getRequestDispatcher("/views/search_film.jsp").forward(request, response);
            case "/film/show_existent_film" -> showExistentFilm(request, response);
            case "/film/show" -> showFilm(request, response);
            case "/film/edit" -> editFilm(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND, "action: " + action);
        }
    }

    /**
     * Handles the HTTP POST request for film-related actions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);
        String userID = (String) request.getSession().getAttribute("userID");
        Optional<User> target = getUserFromDatabase(userID);

        if (target.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = target.get();

        switch (action.toLowerCase()) {
            case "/film/add" -> addFilm(request, response, user);
            case "/film/edit" -> updateFilm(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP DELETE request for film-related actions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws IOException if an input or output error is detected when the servlet handles the DELETE request
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = getAction(request);

        if (action.equalsIgnoreCase("/film/delete")) {
            deleteFilm(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Retrieves the action from the request URL.
     *
     * @param request the HttpServletRequest object
     * @return the action extracted from the request URL
     */
    private String getAction(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        return (pathInfo != null) ? servletPath + pathInfo : servletPath;
    }

    /**
     * Retrieves the user from the database based on the user ID.
     *
     * @param userID the ID of the user
     * @return an Optional containing the user if found, or an empty Optional otherwise
     */
    private Optional<User> getUserFromDatabase(String userID) {
        UserRepository ur = new UserRepository();
        return ur.getUserById(userID);
    }

    /**
     * Handles the request to show existent films based on the search criteria.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected
     */
    private void showExistentFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String language = request.getParameter("language");
        int page = Integer.parseInt(request.getParameter("page"));

        TMDBRequest tmdbRequest = TMDBRequest.getInstance();
        String jsonString = tmdbRequest.searchMovies(title, language, page);

        if (jsonString == null || jsonString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "The request at TMDB return an empty string.");
            return;
        }

        JsonElement je = JsonParser.parseString(jsonString);

        List<JsonObject> searchResultsList = new ArrayList<>();
        if (je != null && je.isJsonObject()) {
            JsonObject searchResultsObject = je.getAsJsonObject();
            JsonArray resultsArray = searchResultsObject.getAsJsonArray("results");

            if (resultsArray != null) {
                for (JsonElement element : resultsArray) {
                    if (element.isJsonObject()) {
                        searchResultsList.add(element.getAsJsonObject());
                    }
                }
            }
        }

        if (searchResultsList.isEmpty()) {
            request.setAttribute("noResults", true);
        }
        request.setAttribute("searchResults", searchResultsList);
        request.getRequestDispatcher("/views/existent_film.jsp").forward(request, response);
    }

    /**
     * Handles the request to show the details of a film.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected
     */
    private void showFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int filmId = Integer.parseInt(request.getParameter("id"));
        FilmRepository filmRepository = new FilmRepository();
        Optional<Film> film = filmRepository.getFilmById(filmId);
        if (film.isPresent()) {
            request.setAttribute("film", film.get());
            request.getRequestDispatcher("/views/show_film.jsp").forward(request, response);
        } else {
            handleFilmNotFound(request, response);
        }
    }

    /**
     * Handles the request to edit a film.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected
     */
    private void editFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int filmId = Integer.parseInt(request.getParameter("tmdbId"));
        FilmRepository filmRepository = new FilmRepository();
        Optional<Film> film = filmRepository.getFilmById(filmId);
        if (film.isPresent()) {
            request.setAttribute("film", film.get());
            request.getRequestDispatcher("/views/edit_film.jsp").forward(request, response);
        } else {
            handleFilmNotFound(request, response);
        }
    }

    /**
     * Handles the request to add a film to the user's library.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param user     the User object representing the current user
     * @throws IOException if an input or output error is detected
     */
    private void addFilm(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        int tmdbId = Integer.parseInt(request.getParameter("tmdbId"));
        String lang = request.getParameter("lang");
        String support = request.getParameter("support");

        Film film = TMDBUtils.getFilmFromTMDB(tmdbId, lang, support);
        film.setLibraryId(user.getLibrary().getId());

        FilmRepository filmRepository = new FilmRepository();
        filmRepository.createFilm(film);

        response.sendRedirect(request.getContextPath() + "/library");
    }

    /**
     * Handles the request to update a film.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected
     */
    private void updateFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int filmId = Integer.parseInt(request.getParameter("id"));
        String lang = request.getParameter("lang");
        String support = request.getParameter("support");
        float rate = Float.parseFloat(request.getParameter("rate"));
        String opinion = request.getParameter("opinion");

        FilmRepository filmRepository = new FilmRepository();
        Optional<Film> film = filmRepository.getFilmById(filmId);
        if (film.isPresent()) {
            Film updatedFilm = film.get();
            updatedFilm.setLang(lang);
            updatedFilm.setSupport(support);
            updatedFilm.setRate(rate);
            updatedFilm.setOpinion(opinion);
            filmRepository.updateFilm(updatedFilm);
            this.successMessageBean.setSuccessMessage("The film has been updated!");
            response.sendRedirect(request.getContextPath() + "/library");
        } else {
            handleFilmNotFound(request, response);
        }
    }

    /**
     * Handles the request to delete a film.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an input or output error is detected
     */
    private void deleteFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int filmId = Integer.parseInt(request.getParameter("tmdbid"));
        FilmRepository filmRepository = new FilmRepository();
        filmRepository.deleteFilm(filmId);
        response.sendRedirect(request.getContextPath() + "/library");
    }

    /**
     * Handles the case when a film is not found.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error is detected
     */
    private void handleFilmNotFound(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.errorMessageBean.setErrorMessage("Film not found.");
        request.getRequestDispatcher("/views/error.jsp").forward(request, response);
    }
}