package com.lmsu.bean.member;

import lombok.Data;

import java.io.Serializable;

public @Data
class CommentObj implements Serializable {
    private String memberID;
    private String memberName;
    private String memberProfilePicturePath;
    private String bookID;
    private String textComment;
    private float rating;
    private String editorID;
    private String editorName;
    private boolean isEdited;

    public CommentObj() {
    }

    public CommentObj(String memberID, String memberName, String memberProfilePicturePath, String bookID,
                      String textComment, float rating, String editorID,
                      String editorName, boolean isEdited) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberProfilePicturePath = memberProfilePicturePath;
        this.bookID = bookID;
        this.textComment = textComment;
        this.rating = rating;
        this.editorID = editorID;
        this.editorName = editorName;
        this.isEdited = isEdited;
    }
}
