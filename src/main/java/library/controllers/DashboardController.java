package library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import library.Book;

import java.io.IOException;

public class DashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label sidebarTotalLabel;

    @FXML private Button navAllBooks;
    @FXML private Button navSearch;
    @FXML private Button navAddBook;
    @FXML private Button navBorrowReturn;

    private AllBooksViewController allBooksViewController;

    @FXML
    public void initialize() {
        showAllBooks();
    }

    @FXML
    public void showAllBooks() {
        loadView("/library/all_books_view.fxml", (loader) -> {
            allBooksViewController = loader.getController();
            allBooksViewController.setDashboard(this);
            allBooksViewController.refreshStats();
        });
        setActiveNav(navAllBooks);
    }

    @FXML
    public void showAddBook() {
        loadView("/library/add_book.fxml", (loader) -> {
            AddBookController ctrl = loader.getController();
            ctrl.setDashboard(this);
        });
        setActiveNav(navAddBook);
    }

    @FXML
    public void showSearch() {
        loadView("/library/search.fxml", null);
        setActiveNav(navSearch);
    }

    @FXML
    public void showBorrowReturn() {
        loadView("/library/borrow_return.fxml", null);
        setActiveNav(navBorrowReturn);
    }

    public void showEditBook(Book book) {
        loadView("/library/edit_book.fxml", (loader) -> {
            EditBookController ctrl = loader.getController();
            ctrl.setDashboard(this);
            ctrl.setBook(book);
        });
        setActiveNav(null); // No nav button active for edit view
    }

    private void loadView(String fxmlPath, ViewInitializer initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            if (initializer != null) {
                initializer.initialize(loader);
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActiveNav(Button activeButton) {
        navAllBooks.getStyleClass().remove("nav-active");
        navSearch.getStyleClass().remove("nav-active");
        navAddBook.getStyleClass().remove("nav-active");
        navBorrowReturn.getStyleClass().remove("nav-active");

        if (activeButton != null) {
            activeButton.getStyleClass().add("nav-active");
        }
    }

    public void updateSidebarCount(int count) {
        sidebarTotalLabel.setText(String.valueOf(count));
    }

    public void refreshAllBooksView() {
        if (allBooksViewController != null) {
            allBooksViewController.refreshTable();
        }
    }

    @FunctionalInterface
    private interface ViewInitializer {
        void initialize(FXMLLoader loader);
    }
}
