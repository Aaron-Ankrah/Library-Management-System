package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.Book;
import library.LibraryData;
import library.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * DashboardController.java
 * ------------------------
 * Controls the main dashboard screen.
 * Shows all books in a table and provides navigation buttons
 * to go to Add Book, Search, and Borrow/Return screens.
 * Also handles Edit and Delete directly from the table.
 *
 * MEMBER RESPONSIBLE: Obed Awuku Mawutor
 */
public class DashboardController implements Initializable {

    // ── Table and its columns (linked to FXML) ───────────────────────────────
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String>  colTitle;
    @FXML private TableColumn<Book, String>  colAuthor;
    @FXML private TableColumn<Book, String>  colGenre;
    @FXML private TableColumn<Book, Integer> colQuantity;
    @FXML private TableColumn<Book, Integer> colAvailable;
    @FXML private Label statusLabel;

    // ── Initializer — runs automatically when screen loads ───────────────────
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        refreshTable();
    }

    /** Reloads the table with the latest book data from Main.books */
    public void refreshTable() {
        bookTable.setItems(null);
        ObservableList<Book> data = FXCollections.observableArrayList(Main.books);
        bookTable.setItems(data);
        bookTable.refresh();
        statusLabel.setText("Total books in library: " + Main.books.size());
    }

    // ── Navigation buttons ───────────────────────────────────────────────────

    @FXML private void openAddBook()      { openScreen("/library/add_book.fxml",      "Add New Book"); }
    @FXML private void openSearch()       { openScreen("/library/search.fxml",         "Search Books"); }
    @FXML private void openBorrowReturn() { openScreen("/library/borrow_return.fxml",  "Borrow / Return Book"); }

    // ── DELETE selected book ─────────────────────────────────────────────────
    @FXML
    private void deleteSelectedBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();

        // Nothing selected — tell the user to pick a row first
        if (selected == null) {
            showInfo("No Book Selected", "Please click on a book in the table first, then click Delete.");
            return;
        }

        // Ask for confirmation before deleting
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Book");
        confirm.setHeaderText("Delete \"" + selected.getTitle() + "\"?");
        confirm.setContentText("This will permanently remove the book from the library.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main.books.remove(selected);
            LibraryData.saveBooks(Main.books);
            refreshTable();
        }
    }

    // ── EDIT selected book ───────────────────────────────────────────────────
    @FXML
    private void editSelectedBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();

        // Nothing selected — tell the user to pick a row first
        if (selected == null) {
            showInfo("No Book Selected", "Please click on a book in the table first, then click Edit.");
            return;
        }

        // Open the Edit screen and pass the selected book to it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/edit_book.fxml"));
            Parent root = loader.load();

            // Give the EditBookController a reference to the book being edited
            EditBookController controller = loader.getController();
            controller.setBook(selected);

            Stage stage = new Stage();
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHidden(e -> refreshTable()); // refresh when edit window closes
            stage.show();

        } catch (IOException e) {
            showAlert("Error", "Could not open Edit screen.");
            e.printStackTrace();
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void openScreen(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setOnHidden(e -> refreshTable());
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not open screen: " + title);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}