package com.lmsu.books;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

public @Data
class BookDTO implements Serializable {
    private String bookID;
    private String title;
    private String authorID;
    private String subjectID;
    private String publisher;
    private String publicationDate;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean deleteStatus;
    private Date lastLentDate;
    private float avgRating;
    private String isbnTen;
    private String isbnThirteen;
    private String coverPath;

    public BookDTO() {
    }

    public BookDTO(String bookID, String title, String authorID, String subjectID, String publisher,
                   String publicationDate, String description, BigDecimal price, int quantity, boolean deleteStatus,
                   Date lastLentDate, float avgRating, String isbnTen, String isbnThirteen, String coverPath) {
        this.bookID = bookID;
        this.title = title;
        this.authorID = authorID;
        this.subjectID = subjectID;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.deleteStatus = deleteStatus;
        this.lastLentDate = lastLentDate;
        this.avgRating = avgRating;
        this.isbnTen = isbnTen;
        this.isbnThirteen = isbnThirteen;
        this.coverPath = coverPath;
    }

    public BookDTO(String bookID, String title, String authorID, boolean deleteStatus) {
        this.bookID = bookID;
        this.title = title;
        this.authorID = authorID;
        this.deleteStatus = deleteStatus;
    }
}
