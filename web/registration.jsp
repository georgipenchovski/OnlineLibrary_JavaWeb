<%@ page import="com.servlets.UserRegistration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="com.servlets.UserRegistration" %>
<html>
<head>
    <title>Registration</title>

    <link rel="stylesheet" type="text/css" href="css/registration.css">

</head>
<body>

<%
    UserRegistration userRegistration = new UserRegistration();
%>

<%

    boolean areWarningsTriggered = false;
    String missingEmail = "";
    boolean isEmailValid = false;
    String missingUsername = "";
    String missingPassword = "";
    String missingRetypePassword = "";
    String[] validEmails = {"@abv.bg", "@gmail.com"};

%>

<%
    if (request.getAttribute("missingEmail") != null) {
        missingEmail = (String) request.getAttribute("missingEmail");
    }
    if (request.getAttribute("missingUsername") != null) {
        missingUsername = (String) request.getAttribute("missingUsername");
    }
    if (request.getAttribute("missingPassword") != null) {
        missingPassword = (String) request.getAttribute("missingPassword");
    }
    if (request.getAttribute("missingRetypePassword") != null) {
        missingRetypePassword = (String) request.getAttribute("missingRetypePassword");
    }
%>
<div align="center">

    <br>

    <h1 class="title">Register</h1>
    <br>
    <p class="errorMessages">${errPassword}${errUsername}
        ${errAllFilled}</p>
    <form method="post" name="register" action="registration">
        <p class="form-group">
            Enter username: &ensp; &#160<input type="text" name="username" size="36"/>
            <br>
            <br>Username can contain any letters or numbers, without spaces
            <br>
            <label><%= missingUsername%>
            </label>
            <br>

            Enter E--mail: &ensp; &#160<input type="text" name="email" size="36"/>
            <br>
            <br>Please provide your E-mail
            <br>
            <label><%= missingEmail%>
            </label>

            <br>

            Enter password: &nbsp; &#160; <input type="password" name="password" size="36"/>
            <br>
            <br>Password should be at least 4 characters
            <br>
            <label><%= missingPassword%>
            </label>

            <br>

            Password (Confirm): &#160<input type="password" name="retypePassword" size="36"/>
            <br>
            <br>Please confirm password
            <br>
            <label><%= missingRetypePassword%>
            </label>

            <br>
            <input type="submit" value="Register">
        </p>
    </form>

</div>
</body>
</html>