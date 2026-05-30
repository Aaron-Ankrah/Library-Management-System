package library;
 
// ─────────────────────────────────────────────────────────────────
//  Main.java  —  Application entry point
//  Author : Mohammed Bonah Adu
//  Purpose: Launches the JavaFX window and owns the shared book list
// ─────────────────────────────────────────────────────────────────
 
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
import java.io.IOException;
 
/**
 * Main — JavaFX Application entry point.
 *
 * Key design decision:
 *   The {@code books} field is {@code public static} so that every
 *   controller in the application can reach the same list without
 *   needing to pass it around. This is the single source of truth
 *   for all book data at runtime.
 */
public class Main extends Application {
 
    // ── Shared Data ───────────────────────────────────────────────
 
    /**
     * The master list of Book objects shared across all controllers.
     *
     * ObservableList automatically notifies any TableView or ListView
     * that is bound to it whenever items are added, removed, or changed.
     * This means the UI updates itself without you writing extra code.
     *
     * IMPORTANT: Never replace this list with a new instance.
     * Always call books.add(), books.remove(), or books.setAll().
     * Replacing the reference breaks any controller that already
     * holds a pointer to the original list.
     */
    public static ObservableList<Book> books =
            FXCollections.observableArrayList();
 
    // ── Application Lifecycle ─────────────────────────────────────
 
    /**
     * start() is called by the JavaFX runtime after the application
     * has been initialised. This is where we:
     *   1. Load saved data from disk into Main.books
     *   2. Load the dashboard FXML layout
     *   3. Build the Scene and display the Stage (window)
     *
     * @param primaryStage  The main window provided by JavaFX.
     * @throws IOException  If the FXML file cannot be found.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
 
        // Step 1 — Load any books previously saved to data/books.txt
        //          LibraryData.loadBooks() returns a List<Book>; we
        //          pass it to setAll() so the ObservableList is filled
        //          without creating a new object.
        LibraryData.loadBooks(books);
 
        // Step 2 — Load the FXML layout for the dashboard screen.
        //
        //          getClass().getResource() looks inside the compiled
        //          resources directory. The path must match exactly:
        //          src/main/resources/library/dashboard.fxml
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/library/dashboard.fxml")
        );
        Parent root = loader.load();
 
        // Step 3 — Create a Scene from the loaded layout.
        //          800 x 600 is the initial window size (width x height).
        Scene scene = new Scene(root, 800, 600);
 
        // Step 4 — Attach the optional stylesheet if it exists.
        //          This line is safe to keep even before styles.css
        //          is written — if the file is missing, JavaFX just
        //          skips it silently.
        scene.getStylesheets().add(
            getClass().getResource("/library/styles.css") != null
                ? getClass().getResource("/library/styles.css").toExternalForm()
                : ""
        );
 
        // Step 5 — Configure and show the primary window.
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);   // Prevent the window from
        primaryStage.setMinHeight(500);  // being resized too small
        primaryStage.show();
    }
 
    // ── Entry Point ───────────────────────────────────────────────
 
    /**
     * main() is the standard Java entry point.
     * It simply calls launch() which kicks off the JavaFX runtime,
     * which in turn calls start() above.
     *
     * @param args  Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}

