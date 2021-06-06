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

    public List<BookDTO> getBookList() {
        return this.bookList;
    }

    public void clearList() {
        bookList.clear();
    }

    public void viewBookList() throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [title], [authorID], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] " +
                        "FROM [Books]" +
                        "WHERE [deleteStatus] = 0 ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String title = rs.getString("title");
                    String authorID = rs.getString("authorID");
                    String subjectID = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date lastLentDate = rs.getDate("lastLentDate");
                    float avg_rating = rs.getFloat("avgRating");
                    String isbnTen = rs.getString("ISBN_tenDigits");
                    String isbnThirteen = rs.getString("ISBN_thirteenDigits");
                    String coverPath = rs.getString("coverPicturePath");
                    BookDTO dto = new BookDTO(bookID, title, authorID, subjectID, publisher, publication_date,
                            description, price, quantity, deleteStatus, lastLentDate,
                            avg_rating, isbnTen, isbnThirteen, coverPath);

                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if book list not existed
                    this.bookList.add(dto);
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
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] " +
                        "FROM [Books] " +
                        "WHERE [title] LIKE ? " +
                        "AND [deleteStatus] = 0 ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String title = rs.getString("title");
                    String authorID = rs.getString("authorID");
                    String subjectID = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date lastLentDate = rs.getDate("lastLentDate");
                    float avg_rating = rs.getFloat("avgRating");
                    String isbnTen = rs.getString("ISBN_tenDigits");
                    String isbnThirteen = rs.getString("ISBN_thirteenDigits");
                    String coverPath = rs.getString("coverPicturePath");
                    BookDTO dto = new BookDTO(bookID, title, authorID, subjectID, publisher, publication_date,
                            description, price, quantity, deleteStatus, lastLentDate,
                            avg_rating, isbnTen, isbnThirteen, coverPath);
                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if bookList not existed
                    this.bookList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void viewBookByAuthor(String authorID) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [title], [authorID], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] " +
                        "FROM [Books] " +
                        "WHERE [authorID] = ? " +
                        "AND [deleteStatus] = 0 ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    //String bookID = rs.getString("id");
                    String title = rs.getString("title");
                    //String authorID = rs.getString("authorID");
                    String subjectID = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publication_date = rs.getString("publishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date lastLentDate = rs.getDate("lastLentDate");
                    float avg_rating = rs.getFloat("avgRating");
                    String isbnTen = rs.getString("ISBN_tenDigits");
                    String isbnThirteen = rs.getString("ISBN_thirteenDigits");
                    String coverPath = rs.getString("coverPicturePath");
                    BookDTO dto = new BookDTO("", title, authorID, subjectID, publisher, publication_date,
                            description, price, quantity, deleteStatus, lastLentDate,
                            avg_rating, isbnTen, isbnThirteen, coverPath);
                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if bookList not existed
                    this.bookList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public BookDTO getBookById(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [title], [authorID], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] " +
                        "FROM [Books] " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    String bookIdVal = rs.getString("id");
                    String title = rs.getString("title");
                    String authorID = rs.getString("authorID");
                    String subjectID = rs.getString("subjectID");
                    String publisher = rs.getString("publisher");
                    String publicationDate = rs.getString("publishDate");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    Date lastLentDate = rs.getDate("lastLentDate");
                    float avgRating = rs.getFloat("avgRating");
                    String isbnTen = rs.getString("ISBN_tenDigits");
                    String isbnThirteen = rs.getString("ISBN_thirteenDigits");
                    String coverPath = rs.getString("coverPicturePath");
                    BookDTO dto = new BookDTO(bookIdVal, title, authorID, subjectID, publisher, publicationDate,
                            description, price, quantity, deleteStatus, lastLentDate,
                            avgRating, isbnTen, isbnThirteen, coverPath);
                    return dto;
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    // return true if ISBN10 is taken, false if ISBN10 is available
    public boolean checkISBNten(String isbnTen) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Books] " +
                        "WHERE [ISBN_tenDigits] LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, isbnTen);
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
    public boolean checkISBNthirteen(String isbnThirteen) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Books] " +
                        "WHERE [ISBN_thirteenDigits] LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, isbnThirteen);
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
    public boolean checkBookId(String bookId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Books] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookId);
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

    public boolean addBook(String bookID, String title, String authorID, String subjectID,
                           String publisher, String publication_date, String description, BigDecimal price, int quantity,
                           boolean deleteStatus, Date lastLentDate, float avgRating, String isbnTen,
                           String isbnThirteen, String coverPath) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO " +
                        "Books([id], [title], [authorID], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] ) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setString(2, title);
                stm.setString(3, authorID);
                stm.setString(4, subjectID);
                stm.setString(5, publisher);
                stm.setString(6, publication_date);
                stm.setString(7, description);
                stm.setBigDecimal(8, price);
                stm.setInt(9, quantity);
                stm.setBoolean(10, deleteStatus);
                stm.setDate(11, lastLentDate);
                stm.setFloat(12, avgRating);
                stm.setString(13, isbnTen);
                stm.setString(14, isbnThirteen);
                stm.setString(15, coverPath);
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

    public boolean deleteBook(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Books] " +
                        "SET [deleteStatus] = ? " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, bookID);
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

    public boolean updateBook(String bookID, String title, String authorID, String subjectID,
                              String publisher, String publicationDate, String description, BigDecimal price, int quantity,
                              String isbnTen, String isbnThirteen)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Books] " +
                        "SET [title] = ?, " +
                        "[authorID] = ?, " +
                        "[subjectID] = ?, " +
                        "[publisher] = ?, " +
                        "[publishDate] = ?, " +
                        "[description] = ?, " +
                        "[price] = ?, " +
                        "[quantity] = ?, " +
                        "[ISBN_tenDigits] = ?, " +
                        "[ISBN_thirteenDigits] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);

                stm.setString(1, title);
                stm.setString(2, authorID);
                stm.setString(3, subjectID);
                stm.setString(4, publisher);
                stm.setString(5, publicationDate);
                stm.setString(6, description);
                stm.setBigDecimal(7, price);
                stm.setInt(8, quantity);
                stm.setString(9, isbnTen);
                stm.setString(10, isbnThirteen);
                stm.setString(11, bookID);
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

    public boolean updateQuantity(String bookID, int quantity)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Books] " +
                        "SET [quantity] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
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

    public void getPopularBooks() throws SQLException, NamingException {
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
                        "WHERE [deleteStatus] = 0" +
                        "ORDER BY [avgRating] desc";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String title = rs.getString("title");
                    String authorID = rs.getString("authorID");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");

                    BookDTO dto = new BookDTO(bookID, title, authorID, deleteStatus);
                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if bookList not existed
                    this.bookList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void getNewArrival() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {                                                   //chưa có data import log nên để tạm data như dưới
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 [id], [title], [authorID], [deleteStatus] " +
                        "FROM [Books] " +
                        "WHERE [deleteStatus] = 0" +
                        "ORDER BY [avgRating] asc";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String title = rs.getString("title");
                    String authorID = rs.getString("authorID");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");

                    BookDTO dto = new BookDTO(bookID, title, authorID, deleteStatus);

                    if (this.bookList == null) {
                        this.bookList = new ArrayList<BookDTO>();
                    } //end if bookList not existed
                    this.bookList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    // Start: Test Paged List
//    public void viewPagedBookList() throws SQLException, NamingException {
//        Connection con = null;
//        PreparedStatement stm = null;
//        ResultSet rs = null;
//
//        try {
//            //1. Connect DB using method built
//            con = DBHelpers.makeConnection();
//            if (con != null) {
//                //2. Create SQL String
//                String sql = "SELECT id, title, authorID, subjectID, publisher, publishDate, description, " +
//                        "price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits " +
//                        "FROM Books ";
//                //3. Create Statement
//                stm = con.prepareStatement(sql);
//                //4. Execute Query and get ResultSet
//                rs = stm.executeQuery();
//                //5. Process ResultSet
//
//                if (this.pagedBookList == null) {
//                    this.pagedBookList = new ArrayList<List<BookDTO>>();
//                } //end if paged book list not existed
//
//                List<BookDTO> unpagedBookList = new ArrayList<BookDTO>();
//
//                while (rs.next()) {
//                    String book_id = rs.getString("id");
//                    String title = rs.getString("title");
//                    String author_id = rs.getString("authorID");
//                    String subject_id = rs.getString("subjectID");
//                    String publisher = rs.getString("publisher");
//                    String publication_date = rs.getString("publishDate");
//                    String description = rs.getString("description");
//                    BigDecimal price = rs.getBigDecimal("price");
//                    int quantity = rs.getInt("quantity");
//                    boolean deleteStatus = rs.getBoolean("deleteStatus");
//                    Date last_lent_date = rs.getDate("lastLentDate");
//                    float avg_rating = rs.getFloat("avgRating");
//                    String isbn_ten = rs.getString("ISBN_tenDigits");
//                    String isbn_thirteen = rs.getString("ISBN_thirteenDigits");
//
//                    BookDTO dto = new BookDTO(book_id, title, author_id, subject_id, publisher, publication_date,
//                            description, price, quantity, deleteStatus, last_lent_date,
//                            avg_rating, isbn_ten, isbn_thirteen);
//
//                    if (!dto.isDelete_status()) {
//                        unpagedBookList.add(dto);
//                    } //end if book is not deleted
//
//                } //end while traversing result set
//
//                int index = 0;
//
//
//
//                while (unpagedBookList.get(index) != null) {
//                    List<BookDTO> singlePageBookList = new ArrayList<BookDTO>();
//                    for (int bookCount = 0; bookCount < 10; bookCount++) {
//                        if (unpagedBookList.get(index) != null) {
//                            singlePageBookList.add(unpagedBookList.get(index));
//                        }
//                        index++;
//                    }
//                    pagedBookList.add(singlePageBookList);
//                }
//
//            } //end if connection existed
//        } finally {
//            if (rs != null) rs.close();
//            if (stm != null) stm.close();
//            if (con != null) con.close();
//        }
//    }
    // End: Test Paged List
}
