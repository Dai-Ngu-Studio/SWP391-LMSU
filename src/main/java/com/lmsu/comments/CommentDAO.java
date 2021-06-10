package com.lmsu.comments;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements Serializable {
    private List<CommentDTO> commentList;

    public List<CommentDTO> getCommentList() {
        return this.commentList;
    }

    public void viewBookComments(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [memberID], [bookID], [textComment], [rating], " +
                        "[editorID], [isEdited], [deleteStatus] " +
                        "FROM [Comments] " +
                        "WHERE [bookID] = ? " +
                        "AND [deleteStatus] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setBoolean(2, false);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String memberID = rs.getString("memberID");
                    String bookIDVal = rs.getString("bookID");
                    String textComment = rs.getString("textComment");
                    float rating = rs.getFloat("rating");
                    String editorID = rs.getString("editorID");
                    boolean isEdited = rs.getBoolean("isEdited");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    CommentDTO dto = new CommentDTO(memberID, bookID, textComment, rating,
                            editorID, isEdited, deleteStatus);
                    if (this.commentList == null) {
                        this.commentList = new ArrayList<CommentDTO>();
                    }
                    this.commentList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean addCommentToBook(String memberID, String bookID, String textComment, float rating)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO [Comments]([memberID], [bookID], [textComment], [rating], [isEdited], [deleteStatus]) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, memberID);
                stm.setString(2, bookID);
                stm.setString(3, textComment);
                stm.setFloat(4, rating);
                stm.setBoolean(5, false);
                stm.setBoolean(6, false);
                //4. Execute Update
                int row = stm.executeUpdate();
                //5. Process result
                if (row > 0) {
                    return true;
                }
            } //end if connection existed
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean editBookComment(String memberID, String bookID, String textComment, String editorID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Comments] " +
                        "SET [textComment] = ?, " +
                        "[editorID] = ?, " +
                        "[isEdited] = ? " +
                        "WHERE [memberID] = ? " +
                        "AND [bookID] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, textComment);
                stm.setString(2, editorID);
                stm.setBoolean(3, true);
                stm.setString(4, memberID);
                stm.setString(5, bookID);
                //4. Execute Update
                int row = stm.executeUpdate();
                //5. Process result
                if (row > 0) {
                    return true;
                }
            } //end if connection existed
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public CommentDTO getCommentOfBookFromUserID(String memberID, String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [memberID], [bookID], [textComment], [rating], " +
                        "[editorID], [isEdited], [deleteStatus] " +
                        "FROM [Comments] " +
                        "WHERE [memberID] = ? " +
                        "AND [bookID] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, memberID);
                stm.setString(2, bookID);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    String memberIDVal = rs.getString("memberID");
                    String bookIDVal = rs.getString("bookID");
                    String textComment = rs.getString("textComment");
                    float rating = rs.getFloat("rating");
                    String editorID = rs.getString("editorID");
                    boolean isEdited = rs.getBoolean("isEdited");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    CommentDTO dto = new CommentDTO( memberIDVal, bookIDVal, textComment,
                            rating, editorID, isEdited, deleteStatus);
                    return dto;
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
}
