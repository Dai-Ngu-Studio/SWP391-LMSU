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
    private List<BookDTO> bookList;
    private List<List<BookDTO>> pagedBookList;

    public List<BookDTO> getBookList() {
        return this.bookList;
    }

    public List<List<BookDTO>> getPagedBookList() {
        return this.pagedBookList;
    }

    // Start: Test Paged List
    public void viewPagedBookList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT id, title, authorID, subjectID, publisher, publishDate, description, " +
                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits " +
                        "FROM Books ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet

                if (this.pagedBookList == null) {
                    this.pagedBookList = new ArrayList<List<BookDTO>>();
                } //end if paged book list not existed

                List<BookDTO> unpagedBookList = new ArrayList<BookDTO>();

                while (rs.next()) {
                    String book_id = rs.getString("id");
                    String title = rs.getString("title");
                    String author_id = rs.getString("authorID");
                    String subject_id = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate");
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

                    if (!dto.isDelete_status()) {
                        unpagedBookList.add(dto);
                    } //end if book is not deleted

                } //end while traversing result set

                int index = 0;

                List<BookDTO> singlePageBookList = new ArrayList<BookDTO>();

                while (unpagedBookList.get(index) != null) {
                    for (int bookCount = 0; bookCount < 10; bookCount++) {
                        if (unpagedBookList.get(index) != null) {
                            singlePageBookList.add(unpagedBookList.get(index));
                        }
                        index++;
                    }
                    pagedBookList.add(singlePageBookList);
                }

            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
    // End: Test Paged List

    public void viewBookList() throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT id, title, authorID, subjectID, publisher, publishDate, description, " +
                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits " +
                        "FROM Books ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String book_id = rs.getString("id");
                    String title = rs.getString("title");
                    String author_id = rs.getString("authorID");
                    String subject_id = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate");
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
                    } //end if book list not existed

                    if (!dto.isDelete_status()) {
                        this.bookList.add(dto);
                    } //end if book is not deleted
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
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
                String sql = "SELECT [id], [title], [authorID], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits] " +
                        "FROM [Books] " +
                        "WHERE [title] LIKE ?";
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
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate"); //db sai tên
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
                    if (!dto.isDelete_status()) {
                        this.bookList.add(dto);
                    } //end if book is not deleted
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    // return true if ISBN10 is taken, false if ISBN10 is available
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
                if (rs.next()) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    // return true if ISBN13 is taken, false if ISBN13 is available
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
                if (rs.next()) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    // return true if ID is taken, false if ID is available
    public boolean checkBookId(String book_id) throws SQLException, NamingException {
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
                        "WHERE id LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, book_id);
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
                        "Books(id, title, authorID, subjectID, publisher, publishDate, description, " +
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
                stm.setBoolean(10, delete_status);
                stm.setDate(11, last_lent_date);
                stm.setFloat(12, avg_rating);
                stm.setString(13, isbn_ten);
                stm.setString(14, isbn_thirteen);
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

    public boolean deleteBook(String book_id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE Books " +
                        "SET deleteStatus = ? " +
                        "WHERE id = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, book_id);
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
                        "SET publishDate = ?, " +
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
                stm.setString(3, subject_id);
                stm.setString(4, publisher);
                stm.setString(5, publication_date);
                stm.setString(6, description);
                stm.setBigDecimal(7, price);
                stm.setInt(8, quantity);
                stm.setString(9, isbn_ten);
                stm.setString(10, isbn_thirteen);
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

    public boolean updateQuantity(String book_id, int quantity)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE Books " +
                        "SET quantity = ? " +
                        "WHERE id = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, book_id);
                stm.setInt(2, quantity);
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
}
