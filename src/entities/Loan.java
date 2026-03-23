package entities;

import java.time.LocalDate;

public class Loan {

    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, LocalDate loanDate, LocalDate returnDate, User user) {
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.user = user;
    }
    public Book getBook() {
        return book;
    }
    public User getUser() {
        return user;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
