package com.lmsu.books;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import com.lmsu.authors.AuthorDTO;
import com.lmsu.importlog.ImportLogDTO;
import com.lmsu.subjects.SubjectDAO;
import com.lmsu.subjects.SubjectDTO;
import lombok.*;

import javax.naming.NamingException;

public @Data
class BookDTO implements Serializable {
    private String bookID;
    private String title;
    private SubjectDTO subject;
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
    private AuthorDTO author;
    private ImportLogDTO importLog;

    public BookDTO() {
    }

    public BookDTO(String bookID, String title, String subjectID, String publisher,
                   String publicationDate, String description, BigDecimal price, int quantity, boolean deleteStatus,
                   Date lastLentDate, float avgRating, String isbnTen, String isbnThirteen, String coverPath) throws SQLException, NamingException {
        this.bookID = bookID;
        this.title = title;
        this.subject = new SubjectDAO().getById(subjectID);
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

    public BookDTO(String bookID, String title, float avgRating, String coverPath, AuthorDTO author) {
        this.bookID = bookID;
        this.title = title;
        this.avgRating = avgRating;
        this.coverPath = coverPath;
        this.author = author;
    }

    public BookDTO(String bookID, String title, String coverPath, AuthorDTO author) {
        this.bookID = bookID;
        this.title = title;
        this.coverPath = coverPath;
        this.author = author;
    }
    public String getSubjectID(){
        return subject.getId();
    }
    public void setSubjectID(String subjectID) throws SQLException, NamingException {
        this.subject = new SubjectDAO().getById(subjectID);
    }
}
