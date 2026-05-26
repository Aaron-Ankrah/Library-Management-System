# 📚 Library Management System — JavaFX

A Library Management System built with Java and JavaFX.

---

## 👥 Team Task Assignments

| Member | File(s) Responsible |
|--------|---------------------|
| ⁠Mohammed Bonah Adu (group leader)— Project Lead     | `Main.java`, GitHub repo, coordination |
| ⁠Anastasia Owusu Nyarko — Book Model       | `Book.java` |
| Awuku Mawutor Obed  — Dashboard        | `DashboardController.java`, `dashboard.fxml` |
| Aaron Ankrah  — Add Book         | `AddBookController.java`, `add_book.fxml` |
| Evans Obiri Yeboah — Search           | `SearchController.java`,  |
| ⁠Sandra Gah — Search UI  | `search.fxml` |
| Nyarko Bismark — Borrow / Return  | `BorrowReturnController.java` |
| Agbeko Rachael Fafali — Borrow / Return UI  | `borrow_return.fxml` |
| Albert Asante Appah — Storage | `LibraryData.java` |
| Precious Gati — Style  | `styles.css` |

---

## 🛠 Requirements

- Java 17 or higher (Java 21 recommended)
- JavaFX SDK 21 — download from: https://gluonhq.com/products/javafx/
  - Choose: JavaFX 21 → SDK → Windows (or your OS) → download & extract

---

## ⚙️ VS Code Setup (do this once, every team member)

1. Install **Extension Pack for Java** from VS Code marketplace
2. Download and extract JavaFX SDK from https://gluonhq.com/products/javafx/
   - Note the path where you extracted it, e.g. `C:\javafx-sdk-21\`
3. Open this `LibrarySystem/` folder in VS Code

---

## ▶️ How to Run (Method 1 — VS Code launch.json)

Create a file at `.vscode/launch.json` inside the project folder with this content
(replace the path with where YOU extracted the JavaFX SDK):

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Run Library System",
            "request": "launch",
            "mainClass": "library.Main",
            "projectName": "LibrarySystem",
            "vmArgs": "--module-path \"C:/javafx-sdk-21/lib\" --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

Then press **F5** or click Run > Start Debugging.

---

## ▶️ How to Run (Method 2 — Terminal, guaranteed to work)

Open a terminal inside the `LibrarySystem/` folder and run these commands:

Step 1 — Compile:
```bash
javac --module-path "C:/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -d out src/main/java/library/*.java src/main/java/library/controllers/*.java
```

Step 2 — Copy resources into out/ folder:
```bash
cp -r src/main/resources/library out/library
```

Step 3 — Run:
```bash
java --module-path "C:/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml -cp out library.Main
```

> On Mac/Linux, replace `C:/javafx-sdk-21/lib` with the actual path where you extracted JavaFX, e.g. `/home/yourname/javafx-sdk-21/lib`

---

## 📁 Project Structure

```
LibrarySystem/
├── src/
│   ├── main/
│   │   ├── java/library/
│   │   │   ├── Main.java                        ← App entry point
│   │   │   ├── Book.java                        ← Book data model
│   │   │   ├── LibraryData.java                 ← File save/load
│   │   │   └── controllers/
│   │   │       ├── DashboardController.java
│   │   │       ├── AddBookController.java
│   │   │       ├── SearchController.java
│   │   │       └── BorrowReturnController.java
│   │   └── resources/library/
│   │       ├── dashboard.fxml
│   │       ├── add_book.fxml
│   │       ├── search.fxml
│   │       ├── borrow_return.fxml
│   │       └── styles.css
├── data/
│   └── books.txt                                ← Auto-saved book data
├── .vscode/
│   └── launch.json                              ← Run config for VS Code
└── README.md
```

---

## ✨ Features

-  Add books (title, author, genre, quantity)
-  View all books in a table on the dashboard
-  Search by title or author in real time
-  Borrow a book (reduces available count)
-  Return a book (restores available count)
-  Data auto-saved to `data/books.txt`

---

## 💡 Tips

- The `data/` folder is created automatically when you first add a book
- `Main.books` is the shared list all controllers use — do not create separate lists
- After any borrow/return/add, call `LibraryData.saveBooks(Main.books)` to persist
- The FXML files define the UI layout — edit them in Scene Builder (optional visual tool)



