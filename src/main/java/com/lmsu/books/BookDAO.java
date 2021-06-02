package com.lmsu.books;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class BookDAO implements Serializable {
    List<BookDTO> bookList;

    public List<BookDTO> getBookList() {
        return this.bookList;
    }

    public void viewBookList() throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT id, title, authorID, subjectID, publisher, puplishDate, description, " +
                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits " +
                        "FROM Books ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String book_id = rs.getString("id");
                    String title = rs.getString("title");
                    String author_id = rs.getString("authorID");
                    String subject_id = rs.getString("subjectID");
                    String publisher = rs.getString("pulisher");
                    String publication_date = rs.getString("pulishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date last_lent_date = rs.getDate("lastLentDate");
                    float avg_rating = rs.getFloat("avgRating");
                    String isbn_ten = rs.getString("ISBN_tenDigits");
                    String isbn_thirteen = rs.getString("ISBN_thirteenDigits");
                    BookDTO dto = new BookDTO(book_id, title, author_id, subject_id, publisher, publication_date,
                            description, price, quantity, deleteStatus, last_lent_date,
                            avg_rating, isbn_ten, isbn_thirteen);
                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    }
                    this.bookList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void searchBookByTitle(String searchValue) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT id, title, authorID, subjectID, publisher, puplishDate, description, " +
                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits " +
                        "FROM Books " +
                        "WHERE title LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String book_id = rs.getString("id");
                    String title = rs.getString("title");
                    String author_id = rs.getString("authorID");
                    String subject_id = rs.getString("subjectID");
                    String publisher = rs.getString("pulisher");
                    String publication_date = rs.getString("pulishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date last_lent_date = rs.getDate("lastLentDate");
                    float avg_rating = rs.getFloat("avgRating");
                    String isbn_ten = rs.getString("ISBN_tenDigits");
                    String isbn_thirteen = rs.getString("ISBN_thirteenDigits");
                    BookDTO dto = new BookDTO(book_id, title, author_id, subject_id, publisher, publication_date,
                            description, price, quantity, deleteStatus, last_lent_date,
                            avg_rating, isbn_ten, isbn_thirteen);
                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if bookList not existed
                    this.bookList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean checkISBNten(String isbn_ten) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT id " +
                        "FROM Books " +
                        "WHERE ISBN_tenDigits LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, isbn_ten);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean checkISBNthirteen(String isbn_thirteen) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT id " +
                        "FROM Books " +
                        "WHERE ISBN_thirteenDigits LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, isbn_thirteen);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean addBook(String book_id, String title, String author_id, String subject_id,
                           String publisher, String publication_date, String description, BigDecimal price, int quantity,
                           boolean delete_status, Date last_lent_date, float avg_rating, String isbn_ten,
                           String isbn_thirteen) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO " +
                        "Books(id, title, authorID, subjectID, publisher, puplishDate, description, " +
                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, book_id);
                stm.setString(2, title);
                stm.setString(3, author_id);
                stm.setString(4, subject_id);
                stm.setString(5, publisher);
                stm.setString(6, publication_date);
                stm.setString(7, description);
                stm.setBigDecimal(8, price);
                stm.setInt(9, quantity);
                stm.setBoolean(10,delete_status);
                stm.setDate(11, last_lent_date);
                stm.setFloat(12,avg_rating);
                stm.setString(13,isbn_ten);
                stm.setString(14,isbn_thirteen);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean deleteBook(String book_id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM Books "
                        + "WHERE id = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, book_id);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateBook(String book_id, String title, String author_id, String subject_id,
                      String publisher, String publication_date, String description, BigDecimal price, int quantity,
                      String isbn_ten, String isbn_thirteen)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE Books " +
                        "SET title = ? , " +
                        "SET authorID = ?, " +
                        "SET subjectID = ?, " +
                        "SET publisher = ?, " +
                        "SET puplishDate = ?, " +
                        "SET description = ?, " +
                        "SET price = ?, " +
                        "SET quantity = ?, " +
                        "SET isbn_ten = ?, " +
                        "SET isbn_thirteen = ?, " +
                        "WHERE id = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, book_id);
                stm.setString(2, author_id);
                stm.setString(3,subject_id);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}