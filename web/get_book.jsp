<%@ page import="java.util.ArrayList" %>
<%@ page import="booksdatabase.Book" %>
<%@ page import="com.utils.ConnectionBuilder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Get book</title>

    <link rel="stylesheet" type="text/css" href="css/get_book.css">

</head>
<body align="center">
<%
    ConnectionBuilder library = new ConnectionBuilder();
    String enteredText = request.getParameter("bookName").trim();
    ArrayList<Book> books = library.getBooks();
    boolean isAvailable = false;

    for (int i = 0; i < books.size(); i++) {
        if (!isAvailable) {
            if (books.get(i).getBookName().equalsIgnoreCase(enteredText) && books.get(i).isAvailable()) {
                isAvailable = true;
            }
        }
    }
%>

<%
    if (isAvailable) {%>
<h1><%= "You just took " + enteredText + ". Have a nice read!"%></h1>
<% library.setAvailability(enteredText, false); } else { %>
<h1><%= enteredText + " is not available or doesn't exist in our library list"%>
</h1>
<%}%>

<form name="demoForm" action="successful_reg.jsp" method="post">
    <input type="submit" value="Back to the Book List">
</form>

</body>
</html>
