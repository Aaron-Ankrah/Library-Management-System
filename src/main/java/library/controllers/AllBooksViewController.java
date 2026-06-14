package library.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Book;
import library.LibraryData;
import library.Main;

public class AllBooksViewController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String>  titleColumn;
    @FXML private TableColumn<Book, String>  authorColumn;
    @FXML private TableColumn<Book, String>  genreColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;
    @FXML private TableColumn<Book, Integer> availableColumn;

    @FXML private Label totalBooksLabel;
    @FXML private Label availableLabel;
    @FXML private Label borrowedLabel;

    @FXML private Button editBtn;
    @FXML private Button deleteBtn;

    private DashboardController dashboard;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        bookTable.setItems(Main.books);

        // Enable Edit/Delete only when a row is selected
        bookTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                boolean selected = newVal != null;
                editBtn.setDisable(!selected);
                deleteBtn.setDisable(!selected);
            }
        );

        // Refresh stats whenever the book list changes
        Main.books.addListener((ListChangeListener<Book>) c -> refreshStats());

        refreshStats();
    }

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    public void refreshStats() {
        Platform.runLater(() -> {
            int total         = Main.books.size();
            int totalCopies   = Main.books.stream().mapToInt(Book::getQuantity).sum();
            int availCopies   = Main.books.stream().mapToInt(Book::getAvailable).sum();
            int borrowed      = Math.max(totalCopies - availCopies, 0);

            totalBooksLabel.setText(String.valueOf(total));
            availableLabel.setText(String.valueOf(availCopies));
            borrowedLabel.setText(String.valueOf(borrowed));

            if (dashboard != null) {
                dashboard.updateSidebarCount(total);
            }
        });
    }

    public void refreshTable() {
        bookTable.refresh();
        refreshStats();
    }

    @FXML
    private void handleEditBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected != null && dashboard != null) {
            dashboard.showEditBook(selected);
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Main.books.remove(selected);
            LibraryData.saveBooks(Main.books);
        }
    }
}
