package ui;

import entities.Admin;
import entities.Book;
import entities.Loan;
import entities.Person;
import entities.User;
import enums.StatusBook;
import services.BookService;
import services.LoanService;
import services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryConsoleUI {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;
    private final Admin admin;
    private final Scanner scanner;

    public LibraryConsoleUI(BookService bookService, UserService userService, LoanService loanService, Admin admin) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option;

        do {
            printMainMenu();
            option = readInt("Escolha uma opcao: ");
            System.out.println();

            switch (option) {
                case 1 -> runAdminArea();
                case 2 -> runUserArea();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida.");
            }

            System.out.println();
        } while (option != 0);
    }

    private void runAdminArea() {
        showActiveProfile(admin);

        int option;

        do {
            printAdminMenu();
            option = readInt("Escolha uma opcao: ");
            System.out.println();

            try {
                switch (option) {
                    case 1 -> registerBook();
                    case 2 -> listBooks();
                    case 3 -> listUsers();
                    case 4 -> listActiveLoans();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        } while (option != 0);
    }

    private void runUserArea() {
        int option;

        do {
            printUserMenu();
            option = readInt("Escolha uma opcao: ");
            System.out.println();

            try {
                switch (option) {
                    case 1 -> registerUser();
                    case 2 -> borrowBook();
                    case 3 -> returnBook();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        } while (option != 0);
    }

    private void printMainMenu() {
        System.out.println("==== LIBRARY SYSTEM ====");
        System.out.println("1 - Area Administrador");
        System.out.println("2 - Area Usuario");
        System.out.println("0 - Sair");
    }

    private void printAdminMenu() {
        System.out.println("==== AREA ADMINISTRADOR ====");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Listar usuarios");
        System.out.println("4 - Listar emprestimos ativos");
        System.out.println("0 - Voltar");
    }

    private void printUserMenu() {
        System.out.println("==== AREA USUARIO ====");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Emprestar livro");
        System.out.println("3 - Devolver livro");
        System.out.println("0 - Voltar");
    }

    private void registerBook() {
        String title = readLine("Titulo: ");
        String author = readLine("Autor: ");

        Book book = new Book(title, author, StatusBook.AVAILABLE);
        bookService.addBook(book);
        System.out.println("Livro cadastrado com sucesso.");
    }

    private void listBooks() {
        List<Book> books = bookService.listBooks();

        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d) %s - %s [%s]%n", i + 1, book.getTitle(), book.getAuthor(), book.getStatus());
        }
    }

    private void registerUser() {
        String name = readLine("Nome: ");
        String email = readLine("Email: ");

        User user = new User(name, email);
        userService.addUser(user);
        System.out.println("Usuario cadastrado com sucesso.");
    }

    private void listUsers() {
        List<User> users = userService.listUsers();

        if (users.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%d) %s - %s%n", i + 1, user.getName(), user.getEmail());
        }
    }

    private void borrowBook() {
        String email = readLine("Email do usuario: ");
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario nao encontrado.");
        }

        String title = readLine("Titulo do livro: ");
        Optional<Book> bookOpt = bookService.findByTitle(title);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Livro nao encontrado.");
        }

        showActiveProfile(userOpt.get());
        loanService.borrowBook(userOpt.get(), bookOpt.get(), LocalDate.now());
        System.out.println("Emprestimo realizado com sucesso.");
    }

    private void returnBook() {
        String title = readLine("Titulo do livro: ");
        Optional<Book> bookOpt = bookService.findByTitle(title);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Livro nao encontrado.");
        }
        loanService.returnBook(bookOpt.get(), LocalDate.now());
        System.out.println("Devolucao registrada com sucesso.");
    }

    private void listActiveLoans() {
        List<Loan> activeLoans = loanService.listActiveLoans();

        if (activeLoans.isEmpty()) {
            System.out.println("Nenhum emprestimo ativo.");
            return;
        }

        for (int i = 0; i < activeLoans.size(); i++) {
            Loan loan = activeLoans.get(i);
            System.out.printf("%d) Livro: %s | Usuario: %s | Data: %s%n",
                    i + 1,
                    loan.getBook().getTitle(),
                    loan.getUser().getEmail(),
                    loan.getLoanDate());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private void showActiveProfile(Person person) {
        System.out.printf("Perfil ativo: %s (%s)%n", person.getProfileType(), person.getEmail());
    }
}
