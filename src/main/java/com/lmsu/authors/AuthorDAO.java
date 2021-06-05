package com.lmsu.authors;

import com.lmsu.books.BookDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements Serializable {
    private List<AuthorDTO> authorList;

    public List<AuthorDTO> getAuthorList() {
        return this.authorList;
    }

    public String getAuthorName(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [name] " +
                        "FROM [Authors] " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet then returns author name
                if (rs.next()) return rs.getString("name");
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public void getPopularAuthor() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 [id], [title], [authorID], [deleteStatus] " +
                        "FROM [Books] " +
                        "ORDER BY avgRating desc";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String authorID = rs.getString("id");
                    String authorName = rs.getString("name");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean deleteStatus = rs.getBoolean("deleteStatus"); //thiếu isDelete và lượt xem trong DB

                    AuthorDTO dto = new AuthorDTO(authorID, authorName, profilePicturePath);
                    if (this.authorList == null) {
                        this.authorList = new ArrayList<AuthorDTO>();
                    } //end if bookList not existed
//                    if (!dto.isDelete_status()) {
//                        this.bookList.add(dto);
//                    } //end if book is not deleted
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
