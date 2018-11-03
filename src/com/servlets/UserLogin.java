package com.servlets;

import com.utils.Constants;
import com.utils.ConnectionBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Login")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ConnectionBuilder connectionBuilder = new ConnectionBuilder();

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("Registration") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!allFieldsFilled(request, response) || !checkUsernameAndPassword(request, response)) {
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("successful_reg.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Makes checks for existing user with that user name and for matching user name
     * and password
     *
     * @param request  - {@link HttpServletRequest}
     * @param response - {@link HttpServletResponse}
     * @return true if all checks are passed
     * @throws IOException      - {@link IOException}
     * @throws ServletException - {@link ServletException}
     */
    private boolean checkUsernameAndPassword(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String username = request.getParameter("UsernameLogin");
        String password = request.getParameter("Password");

        try {
            if (!connectionBuilder.findUser(username)) {
                request.setAttribute("errUser", "User with that username does not exists!");
                getServletContext().getRequestDispatcher("index.jsp").forward(request, response);
                return false;
            }
            if (!matchingUserAndPassword(request, username, password)) {
                request.setAttribute("errUser", "Username and password do not match!");
                getServletContext().getRequestDispatcher("index.jsp").forward(request, response);
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the given user name and password are matching in the database
     *
     * @param username - the user name we are searching for
     * @param password - the password we are searching for
     * @param request  - {@link HttpServletRequest}
     * @return true if the in the database there is a row with given username and
     * password
     * @throws IOException  - {@link IOException}
     * @throws SQLException - {@link SQLException}
     */
    private boolean matchingUserAndPassword(HttpServletRequest request, String username, String password)
            throws SQLException {

        String query = "SELECT * FROM users" + " WHERE username=?";

        try(Connection connection = connectionBuilder.setupDbConnection();
        PreparedStatement stmt = connection.prepareStatement(query);) {

            stmt.setString(1, username);

            try (ResultSet resultset = stmt.executeQuery();) {
                if (resultset.next()) {
                    String currentPassword = resultset.getString("password");
                    if (currentPassword.equals(password)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    private boolean allFieldsFilled(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("UsernameLogin").isEmpty() || request.getParameter("Password").isEmpty()) {
            request.setAttribute("errAllFilled", Constants.ERR_ALL_FILLED);
            getServletContext().getRequestDispatcher("index.jsp").forward(request, response);

            return false;
        }
        return true;
    }
}