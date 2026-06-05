package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import library.Book;
import library.LibraryData;
import library.Main;

/**
 * AddBookController.java
 * ----------------------
 * Controls the Add Book screen.
 * Reads values from the input form, validates them,
 * creates a new Book, and saves it to the list and file.
 *
 * MEMBER RESPONSIBLE: Precious Gati
 */
public class AddBookController {

    // ── Form fields (linked to FXML) ─────────────────────────────────────────
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField genreField;
    @FXML private TextField quantityField;
    @FXML private Label feedbackLabel;

    // ── Save button action ───────────────────────────────────────────────────
    @FXML
    private void saveBook() {
        // Read input from the form fields
        String title    = titleField.getText().trim();
        String author   = authorField.getText().trim();
        String genre    = genreField.getText().trim();
        String qtyText  = quantityField.getText().trim();

        // Validate — make sure no field is empty
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || qtyText.isEmpty()) {
            showFeedback("⚠ Please fill in all fields.", "red");
            return;
        }

        // Validate — quantity must be a positive number
        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showFeedback("⚠ Quantity must be a positive number.", "red");
            return;
        }

        // Create the new book and add it to the shared list
        Book newBook = new Book(title, author, genre, quantity);
        Main.books.add(newBook);

        // Save updated list to file immediately
        LibraryData.saveBooks(Main.books);

        showFeedback("✔ Book added successfully!", "green");
        clearForm();
    }

    // ── Clear button action ──────────────────────────────────────────────────
    @FXML
    private void clearForm() {
        titleField.clear();
        authorField.clear();
        genreField.clear();
        quantityField.clear();
        feedbackLabel.setText("");
    }

    // ── Close button action ──────────────────────────────────────────────────
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    // ── Utility ──────────────────────────────────────────────────────────────
    private void showFeedback(String message, String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
