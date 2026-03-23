package entities;

import enums.StatusBook;

public class Book {

    private String title;
    private String author;
    private StatusBook status;

    public Book(String title, String author, StatusBook status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public StatusBook getStatus() {
        return status;
    }

    public void setStatus(StatusBook status) {
        this.status = status;
    }
}
