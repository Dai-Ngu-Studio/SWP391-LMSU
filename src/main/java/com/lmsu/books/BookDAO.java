package com.lmsu.books;

import com.lmsu.authors.AuthorDTO;
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
        this.bookList.clear();
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
                String sql = "SELECT [id], [title], [subjectID], [publisher], [publishDate], [description], " +
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
                    BookDTO dto = new BookDTO(bookID, title, subjectID, publisher, publication_date,
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
                String sql = "SELECT [id], [title], [subjectID], [publisher], [publishDate], [description], " +
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
                    BookDTO dto = new BookDTO(bookID, title, subjectID, publisher, publication_date,
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
                String sql = "SELECT Books.id, Books.title, Books.subjectID, Books.publisher, Books.publishDate, Books.description, Books.price, " +
                        "Books.quantity, Books.deleteStatus, Books.lastLentDate, Books.avgRating, Books.ISBN_tenDigits, Books.ISBN_thirteenDigits, Books.coverPicturePath " +
                        "FROM AuthorBookMaps " +
                        "RIGHT JOIN Books ON AuthorBookMaps.bookID = Books.id " +
                        "WHERE AuthorBookMaps.authorID = ? " +
                        "AND Books.deleteStatus = 0";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String title = rs.getString("title");
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
                    BookDTO dto = new BookDTO(bookID, title, subjectID, publisher, publication_date,
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
                String sql = "SELECT [id], [title], [subjectID], [publisher], [publishDate], [description], " +
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
                    BookDTO dto = new BookDTO(bookIdVal, title, subjectID, publisher, publicationDate,
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

    public boolean addBook(String bookID, String title, String subjectID,
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
                        "Books([id], [title], [subjectID], [publisher], [publishDate], [description], " +
                        "[price], [quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] ) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setString(2, title);
                stm.setString(3, subjectID);
                stm.setString(4, publisher);
                stm.setString(5, publication_date);
                stm.setString(6, description);
                stm.setBigDecimal(7, price);
                stm.setInt(8, quantity);
                stm.setBoolean(9, deleteStatus);
                stm.setDate(10, lastLentDate);
                stm.setFloat(11, avgRating);
                stm.setString(12, isbnTen);
                stm.setString(13, isbnThirteen);
                stm.setString(14, coverPath);
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

    public boolean updateBook(String bookID, String title, String subjectID,
                              String publisher, String publicationDate, String description, BigDecimal price, int quantity,
                              String isbnTen, String isbnThirteen, String coverPath)
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
                        "[subjectID] = ?, " +
                        "[publisher] = ?, " +
                        "[publishDate] = ?, " +
                        "[description] = ?, " +
                        "[price] = ?, " +
                        "[quantity] = ?, " +
                        "[ISBN_tenDigits] = ?, " +
                        "[ISBN_thirteenDigits] = ?, " +
                        "[coverPicturePath] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);

                stm.setString(1, title);
                stm.setString(2, subjectID);
                stm.setString(3, publisher);
                stm.setString(4, publicationDate);
                stm.setString(5, description);
                stm.setBigDecimal(6, price);
                stm.setInt(7, quantity);
                stm.setString(8, isbnTen);
                stm.setString(9, isbnThirteen);
                stm.setString(10, coverPath);
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
                stm.setInt(1, quantity);
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

    public boolean updateAvgRating(String bookID, float avgRating) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Books] " +
                        "SET [avgRating] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setFloat(1, avgRating);
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

    public BookDTO getBookByISBN13(String ISBN_thirteenDigits) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [title], [subjectID], [publisher], [publishDate], [description], [price], " +
                        "[quantity], [deleteStatus], [lastLentDate], [avgRating], [ISBN_tenDigits], [ISBN_thirteenDigits], [coverPicturePath] " +
                        "FROM [Books] " +
                        "WHERE [ISBN_thirteenDigits] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, ISBN_thirteenDigits);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    String bookIdVal = rs.getString("id");
                    String title = rs.getString("title");
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
                    BookDTO dto = new BookDTO(bookIdVal, title, subjectID, publisher, publicationDate,
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

    public List<BookDTO> getMostFavoriteBooks() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<BookDTO> list = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 Books.id, Books.title, Books.avgRating, Books.coverPicturePath, " +
                        "       STRING_AGG(Authors.name, ', ') AS authors " +
                        "FROM AuthorBookMaps " +
                        "         JOIN Books ON Books.id = AuthorBookMaps.bookID " +
                        "         JOIN Authors ON Authors.id = AuthorBookMaps.authorID " +
                        "WHERE Books.deleteStatus = 0 AND Authors.deleteStatus = 0 " +
                        "GROUP BY Books.id, Books.title, Books.avgRating, Books.coverPicturePath " +
                        "ORDER BY Books.avgRating desc";
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
                    String authorName = rs.getString("authors");

                    AuthorDTO authorDTO = new AuthorDTO(authorName);

                    BookDTO dto = new BookDTO(bookID, bookTitle, avgRating, bookCoverPath, authorDTO);
                    if (list == null) {
                        list = new ArrayList<>();
                    } //end if bookList not existed
                    list.add(dto);
                } //end while traversing result
                return list;
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public List<BookDTO> getNewArrivalBooks() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<BookDTO> list = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 Books.id, Books.title, Books.coverPicturePath,\n" +
                        "             STRING_AGG(Authors.name, ', ') AS authors\n" +
                        "FROM ImportLogs\n" +
                        "         JOIN Books ON Books.id = ImportLogs.bookID\n" +
                        "         JOIN AuthorBookMaps on AuthorBookMaps.bookID = ImportLogs.bookID\n" +
                        "         JOIN Authors ON Authors.id = AuthorBookMaps.authorID\n" +
                        "WHERE Books.deleteStatus = 0 AND Authors.deleteStatus = 0\n" +
                        "GROUP BY Books.id, Books.title, Books.avgRating, Books.coverPicturePath, ImportLogs.dateTaken\n" +
                        "ORDER BY ImportLogs.dateTaken asc\n";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String bookTitle = rs.getString("title");
                    String bookCoverPath = rs.getString("coverPicturePath");
                    String authorName = rs.getString("authors");

                    AuthorDTO authorDTO = new AuthorDTO(authorName);

                    BookDTO dto = new BookDTO(bookID, bookTitle, bookCoverPath, authorDTO);
                    if (list == null) {
                        list = new ArrayList<>();
                    } //end if bookList not existed
                    list.add(dto);
                } //end while traversing result
                return list;
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public List<BookDTO> getPopularAuthorsFromMostFavoriteBooks() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<BookDTO> list = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT TOP 4 Books.id, Books.title, Books.coverPicturePath,\n" +
                        "             Authors.id, Authors.name, Authors.profilePicturePath\n" +
                        "FROM ImportLogs\n" +
                        "         JOIN Books ON Books.id = ImportLogs.bookID\n" +
                        "         JOIN AuthorBookMaps on AuthorBookMaps.bookID = ImportLogs.bookID\n" +
                        "         JOIN Authors ON Authors.id = AuthorBookMaps.authorID\n" +
                        "WHERE Books.deleteStatus = 0 AND Authors.deleteStatus = 0\n" +
                        "ORDER BY Books.avgRating desc";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String bookID = rs.getString("id");
                    String bookTitle = rs.getString("title");
                    String bookCoverPath = rs.getString("coverPicturePath");
                    String authorID = rs.getString("id");
                    String authorName = rs.getString("name");
                    String profilePicturePath = rs.getString("profilePicturePath");

                    AuthorDTO authorDTO = new AuthorDTO(authorID, authorName, profilePicturePath);

                    BookDTO dto = new BookDTO(bookID, bookTitle, bookCoverPath, authorDTO);
                    if (list == null) {
                        list = new ArrayList<>();
                    } //end if bookList not existed
                    list.add(dto);
                } //end while traversing result
                return list;
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public int countBookByAuthorID(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT (*) AS countBooks " +
                        "FROM AuthorBookMaps " +
                        "RIGHT JOIN Books ON AuthorBookMaps.bookID = Books.id " +
                        "WHERE AuthorBookMaps.authorID = ? AND deleteStatus = 0";

                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("countBooks");
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return -1;
    }

    public int getQuantityByBookID(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [quantity] " +
                        "FROM [Books] " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next())
                    return rs.getInt("quantity");
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return -1;
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
