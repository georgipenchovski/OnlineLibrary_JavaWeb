<%@ page import="java.util.ArrayList" %>
<%@ page import="booksdatabase.Book" %>
<%@ page import="com.utils.ConnectionBuilder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Return book</title>

    <link rel="stylesheet" type="text/css" href="css/return_book.css">

</head>
<body align="center">
<%
    ConnectionBuilder library = new ConnectionBuilder();
    String enteredText = request.getParameter("bookName").trim();
    ArrayList<Book> books = library.getBooks();
    boolean isInList = false;

    for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookName().equalsIgnoreCase(enteredText)) {
                isInList = true;
            }
    }
%>

<%
    if (isInList) {%>
<h1><%= "You just returned " + enteredText + ". Thank you!"%>
</h1>
<% library.setAvailability(enteredText,true);} else { %>
<h1><%= enteredText + " is not available or doesn't exist in our library list, so it can't be taken from Vratza Library.\nAlso check for misspelling the book name in the search bar!"%>
<%}%>

    <form name="demoForm" action="successful_reg.jsp" method="post">
        <input type="submit" value="Back to the Book List">
    </form>

</body>
</html>
