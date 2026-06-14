package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import library.Book;
import library.LibraryData;
import library.Main;

public class EditBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField genreField;
    @FXML private TextField quantityField;

    private Book book;
    private DashboardController dashboard;

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    public void setBook(Book book) {
        this.book = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        genreField.setText(book.getGenre());
        quantityField.setText(String.valueOf(book.getQuantity()));
    }

    @FXML
    private void handleSave() {
        if (book == null) {
            showAlert("Error", "No book selected for editing.", Alert.AlertType.ERROR);
            return;
        }

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

        // Update the book
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setQuantity(quantity);

        LibraryData.saveBooks(Main.books);

        showAlert("Success", "Book updated successfully!", Alert.AlertType.INFORMATION);

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
