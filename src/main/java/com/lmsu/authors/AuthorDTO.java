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

    public AuthorDTO(String authorID, String authorName, String authorBio){
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
    }
}
