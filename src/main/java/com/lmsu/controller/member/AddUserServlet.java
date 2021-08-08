package com.lmsu.controller.member;

import com.lmsu.users.AddUserErrorDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.validation.Validation;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddUserServlet", value = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddUserServlet.class);
    private static final String SHOW_USER_CONTROLLER = "ShowMemberServlet";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_USER_CONTROLLER;

        String searchValue = request.getParameter("txtSearchValue");
        String userID = request.getParameter("txtUserID");
        String userName = request.getParameter("txtUserName");
        String roleID = "4";
        String semester = request.getParameter("txtSemester");
        String email = request.getParameter("txtEmail");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String profilePicturePath = "images/default-user-icon.png";
        boolean foundErr = false;

        try {
            UserDAO dao = new UserDAO();
            AddUserErrorDTO error = new AddUserErrorDTO();

            if (!dao.isDelete(userID) && !dao.isDelete(email)) {
                if (!dao.checkUserExisted(userID) && !dao.checkUserExisted(email)) {
                    if (!Validation.isValidUserID(userID)) {
                        foundErr = true;
                        error.setIdError("User ID require input 8 characters or invalid user ID pattern");
                    }
                    if (userName.trim().length() < 2 || userName.trim().length() > 30) {
                        foundErr = true;
                        error.setNameError("User name require input 2 to 30 characters");
                    }
                    try {
                        int semesterNo = Integer.parseInt(semester);

                        if (semesterNo < 0 || semesterNo > 9) {
                            foundErr = true;
                            error.setSemesterError("Semester require input 0 to 9");
                        }
                    } catch (NumberFormatException ex) {
                        foundErr = true;
                        error.setSemesterError("Semester require input 0 to 9");
                    }
                    if (!Validation.isValidEmail(email.toLowerCase().trim())) {
                        foundErr = true;
                        error.setEmailError("Invalid email pattern");
                    }
                    if (!phoneNumber.isEmpty()) {
                        if (!Validation.isValidPhoneNumber(phoneNumber.trim())) {
                            foundErr = true;
                            error.setPhoneNumberError("Phone number require 8 numbers");
                        }
                    }

                    if (foundErr) {
                        request.setAttribute("CREATE_ERROR", error);
                    } else {
                        dao.addUser(userID, userName, roleID, "", email, phoneNumber, semester, profilePicturePath,
                                false, false, false, false);
                    }
                } else {
                    request.setAttribute("ADD_DUPLICATE", "User have existed! Please check again userID or email.");
                }
            } else {
                request.setAttribute("DELETED_USER", "This user have been deleted before. If you want to add again, please undelete it!");
            }

            if (searchValue == null || searchValue.trim().isEmpty()) {
                url = SHOW_USER_CONTROLLER;
            } else {
                url = SEARCH_USER_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddUserServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddUserServlet _ Naming: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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
