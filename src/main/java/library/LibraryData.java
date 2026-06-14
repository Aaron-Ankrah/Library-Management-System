package library;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LibraryData — Handles reading and writing book data to disk.
 *
 * This class is responsible for persistence: it converts Book objects
 * to text lines (serialization) and text lines back to Book objects
 * (deserialization).
 *
 * File format: data/books.txt
 * Each book is stored as a single line with pipe-separated values:
 *   title|author|genre|quantity|available
 *
 * Example line:
 *   Things Fall Apart|Chinua Achebe|Fiction|5|3
 *
 * @author Library Team
 * @version 1.0
 */
public class LibraryData {

    /** Path to the data file relative to project root */
    private static final String DATA_FILE = "data/books.txt";

    /**
     * Loads all books from data/books.txt into the provided list.
     *
     * If the file doesn't exist or is empty, the list remains empty
     * (which is fine — the library just starts with no books).
     *
     * @param books  the ObservableList to populate with loaded books
     */
    public static void loadBooks(ObservableList<Book> books) {
        File file = new File(DATA_FILE);

        // If file doesn't exist, nothing to load — return silently
        if (!file.exists()) {
            System.out.println("No data file found. Starting with empty library.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Parse the line: title|author|genre|quantity|available
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    int available = Integer.parseInt(parts[4]);

                    // Create Book and set available count
                    Book book = new Book(title, author, genre, quantity);
                    book.setAvailable(available);
                    books.add(book);
                }
            }
            System.out.println("Loaded " + books.size() + " books from " + DATA_FILE);

        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing book data: " + e.getMessage());
        }
    }

    /**
     * Saves all books from the list to data/books.txt.
     *
     * This should be called whenever books are added, removed,
     * borrowed, or returned to persist changes to disk.
     *
     * @param books  the list of books to save
     */
    public static void saveBooks(ObservableList<Book> books) {
        File file = new File(DATA_FILE);

        // Ensure the data directory exists
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Book book : books) {
                String line = String.format("%s|%s|%s|%d|%d",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getQuantity(),
                        book.getAvailable());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Saved " + books.size() + " books to " + DATA_FILE);

        } catch (IOException e) {
            System.err.println("Error writing data file: " + e.getMessage());
        }
    }
}
