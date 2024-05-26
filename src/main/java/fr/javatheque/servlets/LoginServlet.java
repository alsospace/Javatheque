package fr.javatheque.servlets;

import fr.javatheque.beans.ErrorMessageBean;
import fr.javatheque.beans.SuccessMessageBean;
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

@WebServlet(name="LoginServlet",urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Inject
    private ErrorMessageBean errorMessageBean;

    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

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
        } else {
            this.errorMessageBean.setErrorMessage("No user or incorrect password.");
        }
        request.getRequestDispatcher("/views/welcome.jsp").forward(request, response);
    }
}
