package library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LibraryData.java
 * ----------------
 * Handles saving and loading books from the data/books.txt file.
 * Every time a book is added, borrowed, or returned — this class
 * saves the updated list so data is not lost when the app closes.
 *
 * MEMBER RESPONSIBLE: Albert Asante Appah
 */
public class LibraryData {

    // Path to the file where all book data is stored
    private static final String FILE_PATH = "data/books.txt";

    /**
     * Saves the entire list of books to books.txt.
     * Each book is saved as one line using Book.toFileString().
     */
    public static void saveBooks(List<Book> books) {
        // Make sure the data/ folder exists before writing
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.toFileString());
                writer.newLine(); // put each book on its own line
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    /**
     * Loads all books from books.txt and returns them as a list.
     * If the file doesn't exist yet, returns an empty list (first run).
     */
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist yet, just return empty list
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    books.add(Book.fromFileString(line)); // convert each line back to a Book
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }

        return books;
    }
}
