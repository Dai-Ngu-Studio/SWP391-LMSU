package com.lmsu.authors;

import com.lmsu.books.BookDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements Serializable {
    private List<AuthorDTO> authorList;

    public List<AuthorDTO> getAuthorList() {
        return this.authorList;
    }

    public void viewAuthorList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [bio], [profilePicturePath] " +
                        "FROM Authors " +
                        "WHERE [deleteStatus] = 0";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String bio = rs.getString("bio");
                    String coverPath = rs.getString("profilePicturePath");

                    AuthorDTO dto = new AuthorDTO(id, name, bio, coverPath);
                    if (this.authorList == null) {
                        this.authorList = new ArrayList<AuthorDTO>();
                    }
                    this.authorList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public AuthorDTO getAuthorByID(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [bio], [profilePicturePath] " +
                        "FROM [Authors] " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet then returns author name
                if (rs.next()) {
                    String authorIdVal = rs.getString("id");
                    String authorName = rs.getString("name");
                    String authorBio = rs.getString("bio");
                    String coverPath = rs.getString("profilePicturePath");
                    AuthorDTO dto = new AuthorDTO(authorIdVal, authorName, authorBio, coverPath);
                    return dto;
                }
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public void searchAuthorByName(String searchVal) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [bio], [profilePicturePath] " +
                        "FROM [Authors] " +
                        "WHERE [name] like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchVal + "%");

                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String bio = rs.getString("bio");
                    String coverPath = rs.getString("profilePicturePath");

                    AuthorDTO dto = new AuthorDTO(id, name, bio, coverPath);
                    if (this.authorList == null) {
                        this.authorList = new ArrayList<AuthorDTO>();
                    }
                    this.authorList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
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

    // return true if ID is taken, false if ID is available
    public boolean checkAuthorId(String authorId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Authors] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorId);
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

    public boolean addAuthor(String authorID, String authorName, String authorBio, String coverPath, boolean deleteStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Authors([id], [name], [bio], [profilePicturePath], [deleteStatus]) " +
                        "VALUES(?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                stm.setString(2, authorName);
                stm.setString(3, authorBio);
                stm.setString(4, coverPath);
                stm.setBoolean(5, deleteStatus);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }

        return false;
    }

    public boolean deleteBook(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Authors] " +
                        "SET [deleteStatus] = ? " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, authorID);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateBook(String authorID, String authorName, String authorBio, String coverPath)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Authors] " +
                        "SET [name] = ?, " +
                        "[bio] = ?, " +
                        "[profilePicturePath] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);

                stm.setString(1, authorName);
                stm.setString(2, authorBio);
                stm.setString(3, coverPath);
                stm.setString(4, authorID);

                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void viewSpecificAuthor(String authorID) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [name], [bio], [coverPicturePath] " +
                        "FROM [Authors] " +
                        "WHERE [authorID] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String authorName = rs.getString("name");
                    String authorBio = rs.getString("bio");
                    String coverPath = rs.getString("coverPicturePath");
                    AuthorDTO dto = new AuthorDTO(authorID, authorName, authorBio, coverPath);
                    if (this.authorList == null) {
                        this.authorList = new ArrayList<AuthorDTO>();
                    } //end if bookList not existed
                    this.authorList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

}
