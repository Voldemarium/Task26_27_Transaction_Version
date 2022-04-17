package Stepanov.homework.Bookstore.ordering_details;

import Stepanov.homework.Bookstore.entity.Book;

public class BookAndQuanity {

    private Book book;

    private int quantity;

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public BookAndQuanity(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

}
