package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import library.Book;
import library.LibraryData;
import library.Main;

public class AddBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField genreField;
    @FXML private TextField quantityField;

    private DashboardController dashboard;

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    private void handleAdd() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String genre = genreField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // Validation
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || quantityText.isEmpty()) {
            showAlert("Validation Error", "All fields are required.", Alert.AlertType.WARNING);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert("Validation Error", "Quantity must be a positive number.", Alert.AlertType.WARNING);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Quantity must be a valid number.", Alert.AlertType.WARNING);
            return;
        }

        // Create and add the book
        Book newBook = new Book(title, author, genre, quantity);
        Main.books.add(newBook);
        LibraryData.saveBooks(Main.books);

        showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);

        // Clear fields
        titleField.clear();
        authorField.clear();
        genreField.clear();
        quantityField.clear();

        // Navigate back to All Books
        if (dashboard != null) {
            dashboard.showAllBooks();
        }
    }

    @FXML
    private void handleCancel() {
        if (dashboard != null) {
            dashboard.showAllBooks();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
