import entities.Admin;
import services.BookService;
import services.LoanService;
import services.UserService;
import ui.LibraryConsoleUI;

public class Main {

    public static void main(String[] args) {
        BookService bookService = new BookService();
        UserService userService = new UserService();
        LoanService loanService = new LoanService();
        Admin admin = new Admin("Admin", "admin@library.com");

        LibraryConsoleUI ui = new LibraryConsoleUI(bookService, userService, loanService, admin);
        ui.start();
    }
}
