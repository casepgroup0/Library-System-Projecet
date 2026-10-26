import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * LibrarySystem - A simple console-based library management program.
 * Allows users to add, borrow, and return books.
 */
public class LibrarySystem {

    // Inner class to represent a Book with title, author, and quantity
    static class Book {
        String title;
        String author;
        int quantity;

        Book(String title, String author, int quantity) {
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }
    }

    // HashMap stores books using the title (lowercase) as the key
    private static Map<String, Book> library = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("      Welcome to the Library System     ");
        System.out.println("========================================");

        while (running) {
            printMenu();

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            // Validate that the input is a number
            if (!input.matches("[1-4]")) {
                System.out.println("\n[ERROR] Invalid choice. Please enter a number between 1 and 4.\n");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    borrowBook(scanner);
                    break;
                case 3:
                    returnBook(scanner);
                    break;
                case 4:
                    System.out.println("\nThank you for using the Library System. Goodbye!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\n--- Library Menu ---");
        System.out.println("1. Add Books");
        System.out.println("2. Borrow Books");
        System.out.println("3. Return Books");
        System.out.println("4. Exit");
    }

    /**
     * Handles adding a new book or updating an existing book's quantity.
     *
     * @param scanner the Scanner object for reading user input
     */
    private static void addBook(Scanner scanner) {
        System.out.println("\n--- Add Book ---");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();

        // Validate that title and author are not empty
        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("[ERROR] Title and author cannot be empty.");
            return;
        }

        System.out.print("Enter quantity: ");
        String qtyInput = scanner.nextLine().trim();

        // Validate that quantity is a positive integer
        if (!qtyInput.matches("\\d+") || Integer.parseInt(qtyInput) <= 0) {
            System.out.println("[ERROR] Quantity must be a positive whole number.");
            return;
        }

        int quantity = Integer.parseInt(qtyInput);
        String key = title.toLowerCase();

        // If book already exists, update quantity; otherwise, add it as new
        if (library.containsKey(key)) {
            library.get(key).quantity += quantity;
            System.out.println("[SUCCESS] Quantity updated. \"" + library.get(key).title
                    + "\" now has " + library.get(key).quantity + " copy/copies.");
        } else {
            library.put(key, new Book(title, author, quantity));
            System.out.println("[SUCCESS] Book added: \"" + title + "\" by " + author
                    + " (" + quantity + " copy/copies).");
        }
    }

    /**
     * Handles borrowing a book from the library.
     *
     * @param scanner the Scanner object for reading user input
     */
    private static void borrowBook(Scanner scanner) {
        System.out.println("\n--- Borrow Book ---");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("[ERROR] Title cannot be empty.");
            return;
        }

        String key = title.toLowerCase();

        // Check if the book exists in the library
        if (!library.containsKey(key)) {
            System.out.println("[ERROR] \"" + title + "\" does not exist in this library.");
            return;
        }

        System.out.print("Enter number of books to borrow: ");
        String qtyInput = scanner.nextLine().trim();

        // Validate quantity input
        if (!qtyInput.matches("\\d+") || Integer.parseInt(qtyInput) <= 0) {
            System.out.println("[ERROR] Please enter a valid positive number.");
            return;
        }

        int quantity = Integer.parseInt(qtyInput);
        Book book = library.get(key);

        // Check if enough copies are available
        if (book.quantity >= quantity) {
            book.quantity -= quantity;
            System.out.println("[SUCCESS] You have borrowed " + quantity + " copy/copies of \""
                    + book.title + "\". Remaining copies: " + book.quantity + ".");
        } else {
            System.out.println("[ERROR] Not enough copies available. Only "
                    + book.quantity + " copy/copies of \"" + book.title + "\" left.");
        }
    }

    /**
     * Handles returning a book to the library.
     *
     * @param scanner the Scanner object for reading user input
     */
    private static void returnBook(Scanner scanner) {
        System.out.println("\n--- Return Book ---");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("[ERROR] Title cannot be empty.");
            return;
        }

        String key = title.toLowerCase();

        // Verify the book belongs to this library system
        if (!library.containsKey(key)) {
            System.out.println("[ERROR] \"" + title
                    + "\" does not belong to this library system. Return rejected.");
            return;
        }

        System.out.print("Enter number of books to return: ");
        String qtyInput = scanner.nextLine().trim();

        // Validate quantity input
        if (!qtyInput.matches("\\d+") || Integer.parseInt(qtyInput) <= 0) {
            System.out.println("[ERROR] Please enter a valid positive number.");
            return;
        }

        int quantity = Integer.parseInt(qtyInput);
        Book book = library.get(key);

        book.quantity += quantity;
        System.out.println("[SUCCESS] You have returned " + quantity + " copy/copies of \""
                + book.title + "\". Updated copies: " + book.quantity + ".");
    }
}
