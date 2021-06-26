package com.lmsu.authorbookmaps;

import com.lmsu.authors.AuthorDAO;
import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDAO;
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

public class AuthorBookMapDAO implements Serializable {

    private List<AuthorBookMapDTO> authorBookMaps;

    public List<AuthorBookMapDTO> getAuthorBookMaps() {
        return this.authorBookMaps;
    }

    public void viewAuthorBookMapList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [authorID], [bookID] " +
                        "FROM [AuthorBookMaps] ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                AuthorDAO authorDAO = new AuthorDAO();
                BookDAO bookDAO = new BookDAO();
                while (rs.next()) {
                    if (this.authorBookMaps == null) {
                        this.authorBookMaps = new ArrayList<AuthorBookMapDTO>();
                    }
                    int mapID = rs.getInt("id");
                    String authorID = rs.getString("authorID");
                    String bookID = rs.getString("bookID");
                    BookDTO bookDTO = bookDAO.getBookById(bookID);
                    AuthorDTO authorDTO = authorDAO.getAuthorByID(authorID);
                    AuthorBookMapDTO authorBookMapDTO = new AuthorBookMapDTO();
                    authorBookMapDTO.setId(mapID);
                    authorBookMapDTO.setBookDTO(bookDTO);
                    authorBookMapDTO.setAuthorDTO(authorDTO);
                    this.authorBookMaps.add(authorBookMapDTO);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void getAuthorsOfBook(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [authorID], [bookID] " +
                        "FROM [AuthorBookMaps] " +
                        "WHERE [bookID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                rs = stm.executeQuery();
                AuthorDAO authorDAO = new AuthorDAO();
                BookDAO bookDAO = new BookDAO();
                while (rs.next()) {
                    if (this.authorBookMaps==null) {
                        this.authorBookMaps = new ArrayList<AuthorBookMapDTO>();
                    }
                    int mapID = rs.getInt("id");
                    String authorID = rs.getString("authorID");
                    BookDTO bookDTO = bookDAO.getBookById(bookID);
                    AuthorDTO authorDTO = authorDAO.getAuthorByID(authorID);
                    AuthorBookMapDTO authorBookMapDTO = new AuthorBookMapDTO();
                    authorBookMapDTO.setId(mapID);
                    authorBookMapDTO.setBookDTO(bookDTO);
                    authorBookMapDTO.setAuthorDTO(authorDTO);
                    this.authorBookMaps.add(authorBookMapDTO);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void getBooksOfAuthor(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [authorID], [bookID] " +
                        "FROM [AuthorBookMaps] " +
                        "WHERE [authorID] = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                rs = stm.executeQuery();
                AuthorDAO authorDAO = new AuthorDAO();
                BookDAO bookDAO = new BookDAO();
                while (rs.next()) {
                    if (this.authorBookMaps==null) {
                        this.authorBookMaps = new ArrayList<AuthorBookMapDTO>();
                    }
                    int mapID = rs.getInt("id");
                    String bookID = rs.getString("bookID");
                    BookDTO bookDTO = bookDAO.getBookById(bookID);
                    AuthorDTO authorDTO = authorDAO.getAuthorByID(authorID);
                    AuthorBookMapDTO authorBookMapDTO = new AuthorBookMapDTO();
                    authorBookMapDTO.setId(mapID);
                    authorBookMapDTO.setBookDTO(bookDTO);
                    authorBookMapDTO.setAuthorDTO(authorDTO);
                    this.authorBookMaps.add(authorBookMapDTO);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
