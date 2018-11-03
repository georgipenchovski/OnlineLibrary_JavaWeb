package com.utils;

import booksdatabase.Book;
import booksdatabase.BooksDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionBuilder {

    private Connection conn;

    public ConnectionBuilder() {
        setupDbConnection();

        if (checkIfTableExists().equalsIgnoreCase("Nqma")) {
            BooksDatabase.initiateDatabase();
            createTable();
            insertData();
        }
        readData();
    }

    public  Connection setupDbConnection() {
        try {
            String uri = "jdbc:mysql://localhost:3306/library?user=root";
            Properties props = setLoginForDB();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(uri, props);
        } catch (Exception e) {
            System.out.print("XXX");
            e.printStackTrace();
        }
        return conn;
    }

    private  Properties setLoginForDB() {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "");
        return props;
    }
    public void createTable() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE books (" + "id SERIAL PRIMARY KEY NOT NULL,"
                    + "name TEXT NOT NULL," + "author TEXT NOT NULL," + "isAvailable boolean DEFAULT true)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertData() {
        for (int i = 0; i < BooksDatabase.getBooks().size(); i++) {
            insertRow(BooksDatabase.getBooks().get(i).getBookName(), BooksDatabase.getBooks().get(i).getBookAuthor());
        }
    }

    public void insertRow(String name, String author) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO books (name, author) VALUES('"
                    + name + "', '" +
                    author + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SELECT * FROM books;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String bookName = rs.getString("name");
                String bookAuthor = rs.getString("author");
                System.out.format("%d %s %s\n", id, bookName, bookAuthor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name, author from books;");

            while (rs.next()) {
                books.add(new Book(rs.getString("name"), rs.getString("author")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void setAvailability(String bookName, boolean takeOrReturn) {
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name, isAvailable from books;");
            while (rs.next()) {
                if (rs.getString("name").equalsIgnoreCase(bookName)) {
                    int i = rs.getRow();
                    stmt.executeUpdate("UPDATE books SET isAvailable = " + takeOrReturn + " WHERE id = '" + i + "';");
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String checkIfTableExists() {
        String query = "SELECT * FROM books WHERE name = '" + "Anna Karenina" + "';";

        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
            }
            return "Ima";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Nqma";
    }

    public boolean findUser(String username) throws IOException, SQLException {
        String query = "SELECT * FROM users" + " WHERE username=\"" + username + "\"";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultset = stmt.executeQuery(query);
        if (resultset.next()) {
            return true;
        }
        return false;
    }
}
