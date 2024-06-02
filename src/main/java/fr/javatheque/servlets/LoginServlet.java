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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet that handles user login requests.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Inject
    private UserBean userBean;

    /**
     * Handles the HTTP GET request for the login page.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request for user login.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserRepository ur = new UserRepository();
        Optional<User> target = ur.getUserByEmail(email);

        if (target.isPresent() && PasswordUtil.verifyPassword(password, target.get().getPassword())) {
            User user = target.get();
            this.userBean.setUserId(user.getId());
            this.userBean.setLastname(user.getLastname());
            this.userBean.setFirstname(user.getFirstname());

            HttpSession session = request.getSession(true);
            session.setAttribute("userID", user.getId());
        } else {
            this.errorMessageBean.setErrorMessage("No user found or incorrect password.");
        }

        request.getRequestDispatcher("/views/welcome.jsp").forward(request, response);
    }
}