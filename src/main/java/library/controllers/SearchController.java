package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import library.Book;
import library.Main;

public class SearchController {

    @FXML private TextField searchField;
    @FXML private Label resultCountLabel;

    @FXML private TableView<Book> resultsTable;
    @FXML private TableColumn<Book, String>  titleColumn;
    @FXML private TableColumn<Book, String>  authorColumn;
    @FXML private TableColumn<Book, String>  genreColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;
    @FXML private TableColumn<Book, Integer> availableColumn;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        resultsTable.setItems(Main.books);
        updateCountLabel(Main.books.size());
    }

    @FXML
    private void handleSearch() {
        applyFilter(searchField.getText().trim());
    }

    @FXML
    private void handleLiveSearch(KeyEvent event) {
        applyFilter(searchField.getText().trim());
    }

    @FXML
    private void handleClear() {
        searchField.clear();
        resultsTable.setItems(Main.books);
        updateCountLabel(Main.books.size());
    }

    private void applyFilter(String term) {
        if (term.isEmpty()) {
            resultsTable.setItems(Main.books);
            updateCountLabel(Main.books.size());
            return;
        }
        String lower = term.toLowerCase();
        ObservableList<Book> filtered = FXCollections.observableArrayList();
        for (Book book : Main.books) {
            if (book.getTitle().toLowerCase().contains(lower)
                    || book.getAuthor().toLowerCase().contains(lower)
                    || book.getGenre().toLowerCase().contains(lower)) {
                filtered.add(book);
            }
        }
        resultsTable.setItems(filtered);
        updateCountLabel(filtered.size());
    }

    private void updateCountLabel(int count) {
        String term = searchField.getText().trim();
        if (term.isEmpty()) {
            resultCountLabel.setText("Showing all " + count + " book" + (count == 1 ? "" : "s"));
        } else {
            resultCountLabel.setText(count + " result" + (count == 1 ? "" : "s") + " for \"" + term + "\"");
        }
    }
}
