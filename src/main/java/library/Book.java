package library;

/**
 * Book.java  —  Data Model for the Library Management System
 *
 * Represents a single book in the library.
 * This is a Plain Old Java Object (POJO): it stores data and
 * exposes it through getter/setter methods. It contains zero
 * JavaFX or UI code — that belongs in the controllers.
 *
 * Used by:
 *   AddBookController      — creates new Book instances
 *   DashboardController    — reads all fields for the TableView
 *   SearchController       — reads title and author for filtering
 *   BorrowReturnController — reads/writes available count
 *   LibraryData            — serialises/deserialises to books.txt
 *
 * @author  Anastasia Owusu Nyarko
 * @version 1.0
 */
public class Book {

    // ── Fields ──────────────────────────────────────────────────────
    // All fields are private so outside code MUST use getters/setters.
    // This is encapsulation — a core OOP principle.

    /** Full title of the book, e.g. "Things Fall Apart" */
    private String title;

    /** Full author name, e.g. "Chinua Achebe" */
    private String author;

    /** Genre or category, e.g. "Fiction", "Science", "History" */
    private String genre;

    /** Total copies owned by the library (does not change after creation) */
    private int quantity;

    /** Copies currently available for borrowing (changes on borrow/return) */
    private int available;


    // ── Constructor ──────────────────────────────────────────────────

    /**
     * Creates a new Book with all required information.
     *
     * When a book is first added to the library, every copy is
     * available, so available is initialised to quantity.
     *
     * @param title    the book title (must not be null or empty)
     * @param author   the author name (must not be null or empty)
     * @param genre    the genre / category
     * @param quantity total number of physical copies (must be > 0)
     */
    public Book(String title, String author, String genre, int quantity) {
        this.title     = title;
        this.author    = author;
        this.genre     = genre;
        this.quantity  = quantity;
        this.available = quantity;  // On creation, all copies are available
    }


    // ── Getters — read-only access to fields ─────────────────────────

    /**
     * Returns the book title.
     * Called by: SearchController, LibraryData, DashboardController.
     * @return the title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author name.
     * Called by: SearchController, LibraryData, DashboardController.
     * @return the author string
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the genre.
     * Called by: LibraryData, DashboardController.
     * @return the genre string
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns total copies owned — never changes after book is added.
     * Called by: LibraryData, DashboardController.
     * @return total quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns how many copies are currently available to borrow.
     * This is the value that changes when books are borrowed/returned.
     *
     * Called by: BorrowReturnController (to check before lending),
     *            DashboardController (Available column),
     *            LibraryData (save/load).
     *
     * @return number of currently available copies (0 to quantity)
     */
    public int getAvailable() {
        return available;
    }


    // ── Setters — controlled write access ────────────────────────────

    /**
     * Updates the title (used if an edit-book feature is added later).
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Updates the author name.
     * @param author the new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Updates the genre.
     * @param genre the new genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Updates the total quantity (e.g. library buys more copies).
     * @param quantity the new total quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the number of currently available copies.
     *
     * This is the most important setter in the system.
     *
     * BorrowReturnController calls it like this:
     *   book.setAvailable(book.getAvailable() - 1);  // borrow
     *   book.setAvailable(book.getAvailable() + 1);  // return
     *
     * LibraryData calls it when loading saved data from books.txt
     * to restore the available count after the app restarts.
     *
     * @param available the updated available count (0 to quantity)
     */
    public void setAvailable(int available) {
        this.available = available;
    }


    // ── toString ─────────────────────────────────────────────────────

    /**
     * Returns a readable description of this Book object.
     * Java calls this automatically when you print a Book, e.g.:
     *   System.out.println(book);
     *
     * Output example:
     *   Book{title='Things Fall Apart', author='Chinua Achebe',
     *        genre='Fiction', quantity=5, available=3}
     *
     * @return formatted string representation
     */
    @Override
    public String toString() {
        return "Book{"                       +
               "title='"      + title      + '\'' +
               ", author='"   + author     + '\'' +
               ", genre='"    + genre      + '\'' +
               ", quantity="  + quantity   +
               ", available=" + available  +
               '}';
    }
}
