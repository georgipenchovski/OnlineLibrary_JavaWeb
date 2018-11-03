package com.servlets;

import com.utils.ConnectionBuilder;
import com.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet(name = "UserRegistration")
public class UserRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ConnectionBuilder connectionBuilder = new ConnectionBuilder();

    /**
     * Method for making a registration of a user
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("Login") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.LOGIN_JSP);
            dispatcher.forward(request, response);
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!checkUsernameAndPassword(request, response, username, password)
                || !register(request, response, username, email, password)) {
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);

    }

    private boolean register(HttpServletRequest request, HttpServletResponse response, String username, String email, String password) {

        String query = "INSERT INTO users"  + " (username, email, password) VALUES (?,?,?)";

            try (Connection connection = connectionBuilder.setupDbConnection();
                 PreparedStatement stmt = connection.prepareStatement(query);) {

                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password);

                stmt.executeUpdate();

            } catch (SQLException e1) {
                return false;
            }

        return true;
    }

    /**
     * Makes checks for already existing user with that user name and for matching
     * password and passwordConfirm
     *
     * @param request  - {@link HttpServletRequest}
     * @param response - {@link HttpServletResponse}
     * @param username - String for user name
     * @param password - String for password
     * @return true if all checks are passed
     * @throws IOException      - {@link IOException}
     * @throws ServletException - {@link ServletException}
     */
    private boolean checkUsernameAndPassword(HttpServletRequest request, HttpServletResponse response, String username,
                                             String password) throws ServletException, IOException {
        try {
            if (connectionBuilder.findUser(username)) {
                getServletContext().getRequestDispatcher("/failed_reg.jsp").forward(request, response);
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

        String passwordConfirm = request.getParameter("retypePassword");
        if (!password.equals(passwordConfirm)) {
            getServletContext().getRequestDispatcher("/failed_reg.jsp").forward(request, response);
            return false;
        }

        return true;
    }

}
