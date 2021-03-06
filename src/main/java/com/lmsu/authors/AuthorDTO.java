package com.lmsu.authors;

import lombok.Data;

import java.io.Serializable;

public @Data
class AuthorDTO implements Serializable {
    private String authorID;
    private String authorName;
    private String authorBio;
    private String coverPath;

    public AuthorDTO() {
    }

    public AuthorDTO(String authorID, String authorName) {
        this.authorID = authorID;
        this.authorName = authorName;
    }

    public AuthorDTO(String authorID, String authorName, String authorBio, String coverPath) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
        this.coverPath = coverPath;
    }

    public AuthorDTO(String authorID, String authorName, String coverPath) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.coverPath = coverPath;
    }

    public AuthorDTO(String authorName) {
        this.authorName = authorName;
    }
}
