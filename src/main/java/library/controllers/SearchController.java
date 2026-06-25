package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Book;
import library.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * SearchController.java
 * ---------------------
 * Controls the Search screen.
 * Filters the book list in real time as the user types
 * in the search bar — matching by title or author.
 *
 * MEMBER RESPONSIBLE: Evans Obiri Yeboah
 */
public class SearchController implements Initializable {

    // ── UI elements (linked to FXML) ─────────────────────────────────────────
    @FXML private TextField searchField;
    @FXML private TableView<Book> resultTable;
    @FXML private TableColumn<Book, String>  colTitle;
    @FXML private TableColumn<Book, String>  colAuthor;
    @FXML private TableColumn<Book, String>  colGenre;
    @FXML private TableColumn<Book, Integer> colQuantity;
    @FXML private TableColumn<Book, Integer> colAvailable;
    @FXML private Label resultCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Link columns to Book fields
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));

        // Show all books when the screen first loads
        showAllBooks();

        // Listen for typing in the search field and filter in real time
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBooks(newValue);
        });
    }

    /** Shows every book in the table (no filter) */
    private void showAllBooks() {
        ObservableList<Book> data = FXCollections.observableArrayList(Main.books);
        resultTable.setItems(data);
        updateCount(Main.books.size());
    }

    /**
     * Filters books where title OR author contains the search query.
     * The search is case-insensitive.
     */
    private void filterBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            showAllBooks();
            return;
        }

        String lowerQuery = query.toLowerCase();

        List<Book> filtered = Main.books.stream()
            .filter(book ->
                book.getTitle().toLowerCase().contains(lowerQuery) ||
                book.getAuthor().toLowerCase().contains(lowerQuery)
            )
            .collect(Collectors.toList());

        resultTable.setItems(FXCollections.observableArrayList(filtered));
        updateCount(filtered.size());
    }

    /** Updates the label showing how many results were found */
    private void updateCount(int count) {
        resultCountLabel.setText(count + " book(s) found");
    }

    @FXML
    private void clearSearch() {
        searchField.clear();
        showAllBooks();
    }
}
