package com.lmsu.comments;

import lombok.Data;

import java.io.Serializable;

public @Data
class CommentDTO implements Serializable {
    private String memberID;
    private String bookID;
    private String textComment;
    private float rating;
    private String editorID;

    public CommentDTO() {
    }

    public CommentDTO(String memberID, String bookID, String textComment, float rating, String editorID) {
        this.memberID = memberID;
        this.bookID = bookID;
        this.textComment = textComment;
        this.rating = rating;
        this.editorID = editorID;
    }
}
