package fr.javatheque.servlets;

import fr.javatheque.beans.ErrorMessageBean;
import fr.javatheque.beans.UserBean;
import fr.javatheque.database.model.User;
import fr.javatheque.database.repository.UserRepository;
import fr.javatheque.util.PasswordUtil;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name="LibraryServlet",urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("email");

        UserRepository ur = new UserRepository();
        Optional<User> target = ur.getUserByEmail(email);

        if (target.isPresent()) {
            User user = target.get();
            request.setAttribute("films", user.getLibrary().getFilms());
        } else {
            this.errorMessageBean.setErrorMessage("User not found.");
        }

        request.getRequestDispatcher("/views/library.jsp").forward(request, response);
    }
}
