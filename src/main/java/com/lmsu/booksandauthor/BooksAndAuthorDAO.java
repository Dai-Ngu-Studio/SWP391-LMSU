package com.lmsu.booksandauthor;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksAndAuthorDAO implements Serializable {

    private List<BooksAndAuthorDTO> booksAndAuthorList;

    public List<BooksAndAuthorDTO> getBooksAndAuthorList() {
        return this.booksAndAuthorList;
    }

    public void clearList() {
        booksAndAuthorList.clear();
    }

    public void getMostFavoriteBooksAndPopularAuthor() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 Books.id, Books.title, Books.authorID AS authorID, Books.avgRating, " +
                        "Books.coverPicturePath, Authors.id, Authors.[name], Authors.profilePicturePath " +
                        "FROM [Books] " +
                        "LEFT JOIN [Authors] ON Books.authorID = Authors.id " +
                        "WHERE Books.deleteStatus = 0 AND Authors.deleteStatus = 0 " +
                        "ORDER BY [avgRating] desc";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String bookTitle = rs.getString("title");
                    float avgRating = rs.getFloat("avgRating");
                    String bookCoverPath = rs.getString("coverPicturePath");
                    String authorID = rs.getString("id");
                    String authorName = rs.getString("name");
                    String profilePicturePath = rs.getString("profilePicturePath");

                    BooksAndAuthorDTO dto = new BooksAndAuthorDTO(bookID, bookTitle, avgRating,
                            bookCoverPath, authorID, authorName, profilePicturePath);
                    if (this.booksAndAuthorList == null) {
                        this.booksAndAuthorList = new ArrayList<BooksAndAuthorDTO>();
                    } //end if bookList not existed
                    this.booksAndAuthorList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
