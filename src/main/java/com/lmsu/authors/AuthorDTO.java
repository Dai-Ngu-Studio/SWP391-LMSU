package com.lmsu.authors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public @Data
class AuthorDTO implements Serializable {
    private String authorID;
    private String authorName;
    private String authorBio;
    private String coverPath;

    public AuthorDTO(String authorID, String authorName, String authorBio, String coverPath){
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
        this.coverPath = coverPath;
    }
    public AuthorDTO(String authorID, String authorName, String coverPath){
        this.authorID = authorID;
        this.authorName = authorName;
        this.coverPath = coverPath;
    }
}
