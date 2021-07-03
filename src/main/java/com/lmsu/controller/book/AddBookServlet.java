package com.lmsu.controller;

import com.lmsu.authorbookmaps.AuthorBookMapDAO;
import com.lmsu.books.BookDAO;
import com.lmsu.books.BookDTO;
import com.lmsu.importlog.ImportLogDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DateHelpers;
import com.lmsu.utils.ImageHelpers;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.apache.commons.io.FilenameUtils;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;

import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "AddBookServlet", value = "/AddBookServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddBookServlet extends HttpServlet {
    private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    private final String SHOW_BOOK_CONTROLLER = "ShowBookServlet";
    //    private final String SEARCH_CONTROLLER = "SearchTitleServlet";
    static final Logger LOGGER = Logger.getLogger(AddBookServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String book_id, String title, String author_id, String subject_id,
        //                           String publisher, String publication_date, String description, BigDecimal price, int quantity,
        //                           boolean delete_status, Date last_lent_date, float avg_rating, String isbn_ten,
        //                           String isbn_thirteen

        String searchVal = request.getParameter("txtSearchValue");
        String addFile = request.getParameter("isAddFile");
        String url = SHOW_BOOK_CONTROLLER;
        UserDTO userDTO = (UserDTO) request.getSession(true).getAttribute("LOGIN_USER");
        if (userDTO == null) {
            System.out.println("You're not logged in yet >:(");
        }
        try {
            boolean result = false;
            String authorID[];
            String title;
            String subjectID;
            String publisher;
            String publishDate;
            String description;
            String price;
            String quantity;
            String isbnTen;
            String isbnThirteen;
            String supplier;
            if (addFile != null) {
                for (Part part : request.getParts()) {
                    if (!(part.getSubmittedFileName() == null || part.getSubmittedFileName().trim().isEmpty())) {
                        if (!FilenameUtils.getExtension(part.getSubmittedFileName()).equalsIgnoreCase("csv")) ;
                        CSVReader csvReader = new CSVReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
                        String[] nextRecord;
                        csvReader.readNext(); //Skip first line
                        int indexRow = 1;
                        ArrayList<Integer> invalidIndexRows = new ArrayList<>();
                        while ((nextRecord = csvReader.readNext()) != null) {
                            indexRow++;
                            boolean readResult = true;
                            if (nextRecord.length < 10) {
                                invalidIndexRows.add(indexRow);
                                readResult = false;
                            } else for (int i = 0; i <= 9; i++) {
                                if (nextRecord[i].trim().isEmpty()) {
                                    invalidIndexRows.add(indexRow);
                                    readResult = false;
                                    break;
                                }
                            }

                            if (readResult) {
                                addBook(request, nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4],
                                        nextRecord[5], nextRecord[6], nextRecord[7], nextRecord[8], nextRecord[9], userDTO.getId(), null, false);
                            }
                        }
                        if (invalidIndexRows.isEmpty() == false) {
                            request.setAttribute("INVALID_ROW_LIST", invalidIndexRows);
                        }
                        break;
                    }
                }
            } else {
                authorID = request.getParameterValues("txtAuthorID");
                title = request.getParameter("txtTitle");
                subjectID = request.getParameter("txtSubjectID");
                publisher = request.getParameter("txtPublisher");
                publishDate = request.getParameter("txtPublishDate");
                description = request.getParameter("txtDescription");
                price = request.getParameter("txtPrice");
                quantity = request.getParameter("txtQuantity");
                isbnTen = request.getParameter("txtISBNTen");
                isbnThirteen = request.getParameter("txtISBNThirteen");
                supplier = request.getParameter("txtSupplier");
                if (title == null) {
                    BookDAO bookDAO = new BookDAO();
                    BookDTO bookAddingExisted = bookDAO.getBookByISBN13(isbnThirteen);
                    result = bookDAO.updateQuantity(bookAddingExisted.getBookID(),
                            Integer.parseInt(quantity) + bookAddingExisted.getQuantity());
                } else {
                    result = addBook(request, title, subjectID, publisher, publishDate,
                            description, price, quantity, isbnTen, isbnThirteen, supplier, userDTO.getId(), authorID, true);
                }
            }
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_BOOK_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookServlet _ Naming: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            log("AddBookServlet _ Exception: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    protected void linkAuthorWithBook(String[] authorID, String bookIDTxt) throws SQLException, NamingException{
        AuthorBookMapDAO authorBookMapDAO = new AuthorBookMapDAO();
        authorBookMapDAO.addMap(authorID,bookIDTxt);
    }

    protected boolean addBook(HttpServletRequest request,
                              String title,
                              String subjectID,
                              String publisher,
                              String publishDate,
                              String description,
                              String price,
                              String quantity,
                              String isbnTen,
                              String isbnThirteen,
                              String supplier,
                              String managerID,
                              String[] authorID,
                              boolean hasCover) throws SQLException, NamingException, ServletException, IOException {

        BookDAO bookDAO = new BookDAO();
        int bookID = 0;
        do {
            bookID++;
        } while (bookDAO.checkBookId(String.valueOf(bookID)));

        String bookIDTxt = String.valueOf(bookID);

        boolean deleteStatus = false;
        BigDecimal priceDecimal = new BigDecimal(price);
        int quantityNum = Integer.parseInt(quantity);
        Date lastLentDate = Date.valueOf("1980-01-01");
        float avgRating = 0.0f;
        //Start to add img to server process
        String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
        String fileName = "";

        if (hasCover)
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();
                if (!(fileName == null || fileName.trim().isEmpty())) {
                    fileName = "book-" + bookIDTxt + "." + FilenameUtils.getExtension(fileName);
                    part.write(uploadPath + fileName);
                    break;
                }
            }

        // Import log
        java.sql.Date currentDate = DateHelpers.getCurrentDate();
        ImportLogDAO importLogDAO = new ImportLogDAO();
        boolean resultAddBook = bookDAO.addBook(bookIDTxt, title, subjectID, publisher, publishDate, description,
                priceDecimal, quantityNum, deleteStatus, lastLentDate, avgRating, isbnTen, isbnThirteen, fileName);
        linkAuthorWithBook(authorID, bookIDTxt);
        boolean resultAddLog = importLogDAO.addLog(bookIDTxt, managerID, currentDate, supplier, quantityNum);
        return resultAddBook && resultAddLog;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
