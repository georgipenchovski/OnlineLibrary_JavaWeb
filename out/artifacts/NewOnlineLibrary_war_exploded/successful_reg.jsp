<%@ page import="java.util.ArrayList" %>
<%@ page import="booksdatabase.Book" %>
<%@ page import="com.utils.ConnectionBuilder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Library</title>

    <link rel="stylesheet" type="text/css" href="css/successful_reg.css">

</head>
<body align="center">
<%
    ConnectionBuilder booksDB = new ConnectionBuilder();
    ArrayList<Book> books = booksDB.getBooks();
%>

<h1 >List of books!</h1>
<table align="center" id="table" border="1">

    <%
        for (int i = 0; i < books.size(); i++) {
    %>
    <tr>
        <td><%= i + 1 %>
        </td>
        <td><%= books.get(i).getBookName() + "  "%>
        </td>
        <td><%= books.get(i).getBookAuthor() %>
        </td>
    </tr>
    <% } %>
</table>
<br/>
<form name="demoForm" action="search_to_get.jsp" method="post">
    <input type="submit" value="Get book">
</form>
<form name="demoForm" action="search_to_return.jsp" method="post">
    <input type="submit" value="Return book">
</form>

</body>
</html>
