package com.lmsu.booksandauthor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class BooksAndAuthorDTO implements Serializable {
    private String bookID;
    private String bookTitle;
    private String subjectID;
    private String publisher;
    private String publishDate;
    private String bookDescription;
    private BigDecimal bookPrice;
    private int bookQuantity;
    private boolean bookDeleteStatus;
    private Date LastLentDate;
    private float avgRating;
    private String isbnTen;
    private String isbnThirteen;
    private String bookCoverPath;
    //----------------------------
    private String authorID;
    //----------------------------
    private String authorName;
    private String authorBio;
    private String authorCoverPath;
    private boolean authorDeleteStatus;

    public BooksAndAuthorDTO(String bookID, String bookTitle, float avgRating,
                             String bookCoverPath, String authorID, String authorName, String authorCoverPath) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.avgRating = avgRating;
        this.bookCoverPath = bookCoverPath;
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorCoverPath = authorCoverPath;
    }
}
