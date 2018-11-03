package booksdatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooksDatabase {

    private static List<String> booksNames = Arrays.asList("Anna Karenina", "Madame Bovary","War and Peace","The Adventures of Huckleberry Finn","The Stories of Anton Chekhov","In Search of Lost Time",
            "Moby Dick","Don Quixote","Crime and Punishment","Jane Eyre");

    private static List<String> booksAuthors = Arrays.asList("Leo Tolstoy", "Gustave Flaubert","Leo Tolstoy","Mark Twain","Anton Chekhov","Marcel Proust",
            "Herman Melville","Miguel de Cervantes","Fyodor Dostoyevsky","Charlotte Bronte");

//
//    private static List<String> booksNames = Arrays.asList("The Hunger Games", "Harry Potter and the Order of the Phoenix", "To Kill a Mockingbird","Pride and Prejudice", "Twilight", "The Book Thief",
//            "The Chronicles of Narnia", "Animal Farm", "Gone with the Wind", "The Fault in Our Stars");
//
//    private static List<String> booksAuthors = Arrays.asList("Suzanne Collins", "J.K. Rowling", "Harper Lee", "Jane Austen", "Stephenie Meyer", "Markus Zusak",
//            "C.S. Lewis", "George Orwell", "Margaret Mitchell", "John Green");

    private static List<Book> books = new ArrayList<>();

    public static void initiateDatabase(){
        for (int i = 0; i < booksNames.size(); i++){
            books.add(new Book(booksNames.get(i), booksAuthors.get(i)));
        }
    }

    public static List<Book> getBooks() {
        return books;
    }

}
