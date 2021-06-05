package com.lmsu.books;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

public @Data
class BookDTO implements Serializable {
    private String book_id;
    private String title;
    private String author_id;
    private String subject_id;
    private String publisher;
    private String publication_date;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean delete_status;
    private Date last_lent_date;
    private float avg_rating;
    private String isbn_ten;
    private String isbn_thirteen;

    public BookDTO() {
    }

    public BookDTO (String book_id, String title, String author_id, String subject_id,
                    String publisher, String publication_date, String description, BigDecimal price, int quantity,
                    boolean delete_status, Date last_lent_date, float avg_rating, String isbn_ten,
                    String isbn_thirteen){
        this.book_id = book_id;
        this.title = title;
        this.author_id = author_id;
        this.subject_id = subject_id;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.delete_status = delete_status;
        this.last_lent_date = last_lent_date;
        this.avg_rating = avg_rating;
        this.isbn_ten = isbn_ten;
        this.isbn_thirteen = isbn_thirteen;
    }

    public BookDTO(String book_id, String title, String author_id, boolean delete_status) {
        this.book_id = book_id;
        this.title = title;
        this.author_id = author_id;
        this.delete_status = delete_status;
    }
}
