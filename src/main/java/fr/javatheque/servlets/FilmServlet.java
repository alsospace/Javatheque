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

@WebServlet(name = "FilmServlet", urlPatterns = {"/film/*"})
public class FilmServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Inject
    private SuccessMessageBean successMessageBean;

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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = getAction(request);

        if (action.equalsIgnoreCase("/film/delete")) {
            deleteFilm(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getAction(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        return (pathInfo != null) ? servletPath + pathInfo : servletPath;
    }

    private Optional<User> getUserFromDatabase(String userID) {
        UserRepository ur = new UserRepository();
        return ur.getUserById(userID);
    }

    private void showExistentFilm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String language = request.getParameter("language");
        int page = Integer.parseInt(request.getParameter("page"));

        TMDBRequest tmdbRequest = TMDBRequest.getInstance();
        String jsonString = tmdbRequest.searchMovies(title, language, page);

        if (jsonString == null || jsonString.isEmpty()){
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "The request at TMDB return an empty string.");
            return;
        }

        JsonObject searchResultsObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray resultsArray = searchResultsObject.getAsJsonArray("results");

        List<JsonObject> searchResultsList = new ArrayList<>();
        for (JsonElement element : resultsArray) {
            searchResultsList.add(element.getAsJsonObject());
        }

        request.setAttribute("searchResults", searchResultsList);
        request.getRequestDispatcher("/views/existent_film.jsp").forward(request, response);
    }

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

    private void addFilm(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        int tmdbId = Integer.parseInt(request.getParameter("tmdbId"));
        String lang = request.getParameter("lang");
        String support = request.getParameter("support");

        Film film = TMDBUtils.getFilmFromTMDB(tmdbId, lang, support, response);
        film.setLibraryId(user.getLibrary().getId());

        FilmRepository filmRepository = new FilmRepository();
        filmRepository.createFilm(film);

        response.sendRedirect(request.getContextPath() + "/library");
    }

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

    private void deleteFilm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int filmId = Integer.parseInt(request.getParameter("tmdbid"));
        FilmRepository filmRepository = new FilmRepository();
        filmRepository.deleteFilm(filmId);
        response.sendRedirect(request.getContextPath() + "/library");
    }
    private void handleFilmNotFound(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.errorMessageBean.setErrorMessage("Film not found.");
        request.getRequestDispatcher("/views/error.jsp").forward(request, response);
    }
}