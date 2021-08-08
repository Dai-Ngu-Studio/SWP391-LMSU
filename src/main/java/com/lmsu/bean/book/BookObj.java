package com.lmsu.bean.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class BookObj implements Serializable {
    Map<String, String> authors;
    private String id;
    private String title;
    private String subjectID;
    private String subjectName;
    private String publisher;
    private String publishDate;
    private String description;
    private int quantity;
    private float avgRating;
    private String isbnTen;
    private String isbnThirteen;
    private String coverPath;
}
