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
    private boolean isEdited;

    public CommentDTO() { }

    public CommentDTO(String memberID, String bookID, String textComment, float rating, String editorID, boolean isEdited) {
        this.memberID = memberID;
        this.bookID = bookID;
        this.textComment = textComment;
        this.rating = rating;
        this.editorID = editorID;
        this.isEdited = isEdited;
    }
}
