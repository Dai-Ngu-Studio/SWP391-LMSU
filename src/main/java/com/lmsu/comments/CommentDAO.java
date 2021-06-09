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
                String sql = "SELECT [memberID], [bookID], [textComment], [rating], [editorID], [isEdited] " +
                        "FROM [Comments] " +
                        "WHERE [bookID] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
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
                    CommentDTO dto = new CommentDTO(memberID, bookID, textComment, rating, editorID, isEdited);
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

    public void addCommentToBook(String memberId, String bookID, String textComment, float rating)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                //3. Create Statement
                //4. Execute Update
                //5. Process result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
