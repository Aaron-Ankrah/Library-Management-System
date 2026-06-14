package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Book;
import library.LibraryData;
import library.Main;

public class BorrowReturnController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String>  titleColumn;
    @FXML private TableColumn<Book, String>  authorColumn;
    @FXML private TableColumn<Book, String>  genreColumn;
    @FXML private TableColumn<Book, Integer> availableColumn;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        bookTable.setItems(Main.books);

        bookTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, book) -> {
                if (book == null) {
                    statusLabel.setText("Select a book to borrow or return.");
                } else if (book.getAvailable() == 0) {
                    statusLabel.setText("\"" + book.getTitle() + "\" — no copies available to borrow.");
                } else {
                    statusLabel.setText("\"" + book.getTitle() + "\" — "
                        + book.getAvailable() + " of " + book.getQuantity() + " copies available.");
                }
            }
        );
    }

    @FXML
    private void handleBorrow() {
        Book book = bookTable.getSelectionModel().getSelectedItem();
        if (book == null) {
            showAlert("No Selection", "Please select a book to borrow.", Alert.AlertType.WARNING);
            return;
        }
        if (book.getAvailable() <= 0) {
            showAlert("Not Available", "All copies of this book are currently on loan.", Alert.AlertType.WARNING);
            return;
        }
        book.setAvailable(book.getAvailable() - 1);
        bookTable.refresh();
        LibraryData.saveBooks(Main.books);
        statusLabel.setText("Borrowed. \"" + book.getTitle() + "\" — "
            + book.getAvailable() + " of " + book.getQuantity() + " copies remaining.");
    }

    @FXML
    private void handleReturn() {
        Book book = bookTable.getSelectionModel().getSelectedItem();
        if (book == null) {
            showAlert("No Selection", "Please select a book to return.", Alert.AlertType.WARNING);
            return;
        }
        if (book.getAvailable() >= book.getQuantity()) {
            showAlert("Cannot Return", "All copies are already in the library.", Alert.AlertType.WARNING);
            return;
        }
        book.setAvailable(book.getAvailable() + 1);
        bookTable.refresh();
        LibraryData.saveBooks(Main.books);
        statusLabel.setText("Returned. \"" + book.getTitle() + "\" — "
            + book.getAvailable() + " of " + book.getQuantity() + " copies available.");
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
