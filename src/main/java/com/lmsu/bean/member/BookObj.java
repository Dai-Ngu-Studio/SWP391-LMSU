package com.lmsu.bean.member;

import lombok.Data;

import java.io.Serializable;

public @Data
class BookObj implements Serializable {
    private String id;
    private String title;
    private String authorName;
    private String subjectName;
    private String publisher;
    private String publishDate;
    private String description;
    private int quantity;
    private float avgRating;
    private String isbnTen;
    private String isbnThirteen;
    private String coverPath;

    public BookObj() {
    }

    public BookObj(String id, String title, String authorName, String subjectName, String publisher,
                   String publishDate, String description, int quantity, float avgRating, String isbnTen,
                   String isbnThirteen, String coverPath) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.subjectName = subjectName;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.quantity = quantity;
        this.avgRating = avgRating;
        this.isbnTen = isbnTen;
        this.isbnThirteen = isbnThirteen;
        this.coverPath = coverPath;
    }
}
