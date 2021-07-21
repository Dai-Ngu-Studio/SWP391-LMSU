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
                    if (this.authorBookMaps == null) {
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
                    if (this.authorBookMaps == null) {
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

    public ArrayList<String> getCannotDeleteAuthors() throws SQLException, NamingException {
        ArrayList<String> listOfCannotDeleteAuthors = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT DISTINCT [o].[authorID] as [doNotDeleteAuthor]" +
                        "FROM [AuthorBookMaps] as [o]" +
                        "RIGHT JOIN (" +
                        "SELECT [bookID] " +
                        "FROM AuthorBookMaps " +
                        "GROUP BY [bookID] " +
                        "HAVING COUNT(*) = 1) as [c] " +
                        "ON [o].[bookID] = [c].[bookID]";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    listOfCannotDeleteAuthors.add(rs.getString("doNotDeleteAuthor"));
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return listOfCannotDeleteAuthors;
    }

    public void deleteByAuthorID(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM [AuthorBookMaps] " +
                        "WHERE [authorID] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get rows affected
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void deleteByBookID(String txtBookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM [AuthorBookMaps] " +
                        "WHERE [bookID] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, txtBookID);
                //4. Execute Query and get rows affected
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean isExistedMap(String authorID, String bookIDTxt) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT * " +
                        "FROM [AuthorBookMaps] " +
                        "WHERE [authorID] LIKE ? AND [bookID] LIKE ?";
//                sql="SELECT [id], [authorID], [bookID] FROM [AuthorBookMaps]";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                stm.setString(2, bookIDTxt);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    //    public void addMap(String[] authorIDs, String bookIDTxt) throws NamingException, SQLException {
//        Connection con = null;
//        PreparedStatement stm = null;
//        ResultSet rs = null;
//        try {
//            con = DBHelpers.makeConnection();
//            if (con != null) {
//                String sql = "INSERT INTO [AuthorBookMaps]([authorID], [bookID]) " +
//                        "VALUES(?, ?)";
//                stm = con.prepareStatement(sql);
//                if (authorIDs != null) {
//                    for (String authorID : authorIDs) {
//                        if (authorID.trim().isEmpty() == false && isExistedMap(authorID, bookIDTxt) == false) {
//                            System.out.println(isExistedMap(authorID, bookIDTxt));
//                            stm.setString(1, authorID);
//                            stm.setString(2, bookIDTxt);
//                            stm.addBatch();
//                        }
//                    }
//                }
//                stm.executeBatch();
//            }
//        } finally {
//            if (rs != null) rs.close();
//            if (stm != null) stm.close();
//            if (con != null) con.close();
//        }
    public void addMap(String[] authorIDs, String bookIDTxt) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO [AuthorBookMaps]([authorID], [bookID]) " +
                        "VALUES(?, ?)";
                stm = con.prepareStatement(sql);
                if (authorIDs != null) {
                    for (String authorID : authorIDs) {
                        if (authorID.trim().isEmpty() == false && isExistedMap(authorID, bookIDTxt) == false) {
                            stm.setString(1, authorID);
                            stm.setString(2, bookIDTxt);
                            stm.executeUpdate();
                        }
                    }
                }

            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void updateMap(String[] authorIDs, String bookIDTxt) throws NamingException, SQLException {
        deleteByBookID(bookIDTxt);
        addMap(authorIDs, bookIDTxt);
    }
}
