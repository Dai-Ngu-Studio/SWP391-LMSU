package com.lmsu.bean.member;

import java.io.Serializable;

public class CommentObj implements Serializable {
    private String memberID;
    private String memberName;
    private String memberProfilePicturePath;
    private String bookID;
    private String textComment;
    private float rating;
    private String editorID;
    private String editorName;
    private boolean isEdited;

    public CommentObj() { }

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

    public String getMemberID() { return memberID; }

    public String getMemberName() { return memberName; }

    public String getMemberProfilePicturePath() { return memberProfilePicturePath; }

    public String getBookID() { return bookID; }

    public String getTextComment() { return textComment; }

    public float getRating() { return rating; }

    public String getEditorID() { return editorID; }

    public String getEditorName() { return editorName; }

    public boolean isEdited() { return isEdited; }
}
