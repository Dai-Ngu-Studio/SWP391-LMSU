package com.lmsu.bean.author;

import lombok.Data;

import java.io.Serializable;

public @Data
class AuthorObj implements Serializable {
    private String authorID;
    private String authorName;
    private String authorBio;
    private String coverPath;

    public AuthorObj() {
    }

    public AuthorObj(String authorID, String authorName, String authorBio) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
    }

    public AuthorObj(String authorID, String authorName, String authorBio, String coverPath) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
        this.coverPath = coverPath;
    }
}
