package services;

import entities.Book;
import entities.Loan;
import entities.User;
import enums.StatusBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoanService {

    private final List<Loan> loans = new ArrayList<>();

    public void borrowBook(User user, Book book, LocalDate loanDate) {
        if (book.getStatus() != StatusBook.AVAILABLE) {
            throw new IllegalArgumentException("Livro indisponivel para emprestimo.");
        }

        Loan loan = new Loan(book, loanDate, null, user);
        loans.add(loan);
        book.setStatus(StatusBook.BORROWED);
    }

    public void returnBook(Book book, LocalDate returnDate) {
        Loan activeLoan = findActiveLoanByBook(book)
                .orElseThrow(() -> new IllegalArgumentException("Nao existe emprestimo ativo para este livro."));

        if (returnDate.isBefore(activeLoan.getLoanDate())) {
            throw new IllegalArgumentException("Data de devolucao nao pode ser antes da data de emprestimo.");
        }

        activeLoan.setReturnDate(returnDate);
        book.setStatus(StatusBook.AVAILABLE);
    }

    public List<Loan> listLoans() {
        return new ArrayList<>(loans);
    }

    public List<Loan> listActiveLoans() {
        return loans.stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public Optional<Loan> findActiveLoanByBook(Book book) {
        return loans.stream()
                .filter(loan -> loan.getBook().equals(book))
                .filter(loan -> loan.getReturnDate() == null)
                .findFirst();
    }
}
