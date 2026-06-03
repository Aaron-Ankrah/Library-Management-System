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
}
