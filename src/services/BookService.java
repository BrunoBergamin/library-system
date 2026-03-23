package services;

import entities.Book;
import enums.StatusBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        boolean bookExists = books.stream()
                .anyMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle())
                        && b.getAuthor().equalsIgnoreCase(book.getAuthor()));
        if (bookExists) {
            throw new IllegalArgumentException("Livro ja cadastrado.");
        }
        books.add(book);
    }
    public List<Book> listBooks() {
        return new ArrayList<>(books);
    }
    public Optional<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
    public List<Book> listAvailableBooks() {
        return books.stream()
                .filter(book -> book.getStatus() == StatusBook.AVAILABLE)
                .collect(Collectors.toList());
    }
}
