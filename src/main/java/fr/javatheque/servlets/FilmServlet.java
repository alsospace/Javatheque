package fr.javatheque.servlets;

import fr.javatheque.beans.ErrorMessageBean;
import fr.javatheque.beans.FilmBean;
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
import java.util.Optional;

@WebServlet(name = "FilmServlet", urlPatterns = {"/film/*"})
public class FilmServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        String userID = (String) request.getSession().getAttribute("userID");

        UserRepository ur = new UserRepository();
        Optional<User> target = ur.getUserById(userID);

        if (target.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (action == null || action.equals("/")) {
            request.getRequestDispatcher("/views/film/library.jsp").forward(request, response);
        } else if (action.equals("/search")) {
            String query = request.getParameter("query");
            String language = request.getParameter("language");
            int page = Integer.parseInt(request.getParameter("page"));

            TMDBRequest tmdbRequest = TMDBRequest.getInstance();
            String searchResults = tmdbRequest.searchMovies(query, language, page);

            request.setAttribute("searchResults", searchResults);
            request.getRequestDispatcher("/views/film/existant_film.jsp").forward(request, response);
        } else if (action.equals("/show")) {
            int filmId = Integer.parseInt(request.getParameter("id"));
            FilmRepository filmRepository = new FilmRepository();
            Optional<Film> film = filmRepository.getFilmById(filmId);
            if (film.isPresent()) {
                request.setAttribute("film", film.get());
                request.getRequestDispatcher("/views/film/show_film.jsp").forward(request, response);
            } else {
                this.errorMessageBean.setErrorMessage("Film not found.");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } else if (action.equals("/edit")) {
            int filmId = Integer.parseInt(request.getParameter("id"));
            FilmRepository filmRepository = new FilmRepository();
            Optional<Film> film = filmRepository.getFilmById(filmId);
            if (film.isPresent()) {
                request.setAttribute("film", film.get());
                request.getRequestDispatcher("/views/film/edit_film.jsp").forward(request, response);
            } else {
                this.errorMessageBean.setErrorMessage("Film not found.");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userID = (String) request.getSession().getAttribute("userID");

        UserRepository ur = new UserRepository();
        Optional<User> target = ur.getUserById(userID);

        if (target.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = target.get();
        String action = request.getPathInfo();

        if (action.equals("/add")) {
            int tmdbId = Integer.parseInt(request.getParameter("tmdbId"));
            String lang = request.getParameter("lang");
            String support = request.getParameter("support");

            Film film = TMDBUtils.getFilmFromTMDB(tmdbId, lang, support);
            film.setLibraryId(user.getLibrary().getId());

            FilmRepository filmRepository = new FilmRepository();
            filmRepository.createFilm(film);

            response.sendRedirect(request.getContextPath() + "/library");

        } else if (action.equals("/edit")) {
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
                response.sendRedirect(request.getContextPath() + "/library");
            } else {
                this.errorMessageBean.setErrorMessage("Film not found.");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();

        if (action.startsWith("/delete")) {
            int filmId = Integer.parseInt(request.getParameter("id"));
            FilmRepository filmRepository = new FilmRepository();
            filmRepository.deleteFilm(filmId);
            response.sendRedirect(request.getContextPath() + "/library");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}