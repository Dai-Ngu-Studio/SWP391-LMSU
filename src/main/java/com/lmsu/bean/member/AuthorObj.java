package com.lmsu.bean.member;

import java.io.Serializable;

public class AuthorObj implements Serializable {
    private String authorID;
    private String authorName;
    private String authorBio;

    public AuthorObj (String authorID, String authorName, String authorBio) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorBio = authorBio;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorBio() {
        return authorBio;
    }
}
