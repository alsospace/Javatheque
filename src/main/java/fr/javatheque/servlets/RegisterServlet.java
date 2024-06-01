package fr.javatheque.servlets;

import fr.javatheque.beans.ErrorMessageBean;
import fr.javatheque.beans.UserBean;
import fr.javatheque.database.model.User;
import fr.javatheque.database.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Inject
    private ErrorMessageBean errorMessageBean;

    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserRepository ur = new UserRepository();

        Optional<User> target = ur.getUserByEmail(email);

        if (target.isEmpty()) {
            User user = ur.createUser(new User(lastname, firstname, email, password, true));
            this.userBean.setUserId(user.getId());
            this.userBean.setLastname(user.getLastname());
            this.userBean.setFirstname(user.getFirstname());

            HttpSession session = request.getSession(true);
            session.setAttribute("userID", user.getId());
        } else {
            this.errorMessageBean.setErrorMessage("This email is already used.");
        }

        request.getRequestDispatcher("/views/welcome.jsp").forward(request, response);
    }
}
