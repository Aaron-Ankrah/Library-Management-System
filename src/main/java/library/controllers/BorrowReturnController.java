package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import library.Book;
import library.LibraryData;
import library.Main;

import java.util.Optional;

/**
 * BorrowReturnController.java
 * ---------------------------
 * Controls the Borrow / Return screen.
 * The user types a book title, searches for it,
 * then clicks Borrow or Return to update its availability.
 *
 * MEMBER RESPONSIBLE: Nyarko Bismark
 */
public class BorrowReturnController {

    // ── UI elements (linked to FXML) ─────────────────────────────────────────
    @FXML private TextField searchField;
    @FXML private Label     titleLabel;
    @FXML private Label     authorLabel;
    @FXML private Label     genreLabel;
    @FXML private Label     availableLabel;
    @FXML private Label     feedbackLabel;
    @FXML private Button    borrowButton;
    @FXML private Button    returnButton;

    // The book that was found by the search
    private Book selectedBook = null;

    // ── Search action ────────────────────────────────────────────────────────
    @FXML
    private void searchBook() {
        String query = searchField.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            showFeedback("⚠ Please enter a book title to search.", "orange");
            return;
        }

        // Look for a matching book in the shared list (title match)
        Optional<Book> result = Main.books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(query))
            .findFirst();

        if (result.isPresent()) {
            selectedBook = result.get();
            displayBookDetails(selectedBook);
            showFeedback("Book found! You can now borrow or return it.", "#2e7d32");
        } else {
            clearBookDetails();
            showFeedback("⚠ No book found with that title.", "red");
        }
    }

    /** Shows the found book's details in the info section */
    private void displayBookDetails(Book book) {
        titleLabel.setText("Title: "     + book.getTitle());
        authorLabel.setText("Author: "   + book.getAuthor());
        genreLabel.setText("Genre: "     + book.getGenre());
        availableLabel.setText("Available: " + book.getAvailable() + " / " + book.getQuantity());

        // Enable the action buttons
        borrowButton.setDisable(false);
        returnButton.setDisable(false);
    }

    private void clearBookDetails() {
        selectedBook = null;
        titleLabel.setText("Title: —");
        authorLabel.setText("Author: —");
        genreLabel.setText("Genre: —");
        availableLabel.setText("Available: —");
        borrowButton.setDisable(true);
        returnButton.setDisable(true);
    }

    // ── Borrow action ────────────────────────────────────────────────────────
    @FXML
    private void borrowBook() {
        if (selectedBook == null) return;

        boolean success = selectedBook.borrowBook();

        if (success) {
            LibraryData.saveBooks(Main.books); // save after change
            availableLabel.setText("Available: " + selectedBook.getAvailable() + " / " + selectedBook.getQuantity());
            showFeedback("✔ Book borrowed successfully!", "#1565c0");
        } else {
            showFeedback("✘ No copies available to borrow.", "red");
        }
    }

    // ── Return action ────────────────────────────────────────────────────────
    @FXML
    private void returnBook() {
        if (selectedBook == null) return;

        boolean success = selectedBook.returnBook();

        if (success) {
            LibraryData.saveBooks(Main.books); // save after change
            availableLabel.setText("Available: " + selectedBook.getAvailable() + " / " + selectedBook.getQuantity());
            showFeedback("✔ Book returned successfully!", "#2e7d32");
        } else {
            showFeedback("✘ No borrowed copies to return.", "red");
        }
    }

    // ── Utility ──────────────────────────────────────────────────────────────
    private void showFeedback(String message, String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
