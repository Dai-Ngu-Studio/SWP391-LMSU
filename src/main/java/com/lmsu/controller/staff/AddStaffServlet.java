package com.lmsu.controller.staff;

import com.google.common.hash.Hashing;
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
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "AddStaffServlet", value = "/AddStaffServlet")
public class AddStaffServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddStaffServlet.class);
    private static final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";
    private static final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = SHOW_STAFF_CONTROLLER;

        String searchValue = request.getParameter("txtSearchValue");
        String userID = request.getParameter("txtUserID");
        String userName = request.getParameter("txtUserName");
        String roleID = request.getParameter("txtRoleID");
        String password = request.getParameter("txtPassword");
        String passwordHashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        String email = request.getParameter("txtEmail");
        String phoneNumber = request.getParameter("txtPhoneNumber");
        String profilePicturePath = "images/default-user-icon.png";

        try {
            UserDAO dao = new UserDAO();
            AddUserErrorDTO error = new AddUserErrorDTO();
            boolean foundErr = false;

            if (roleID.equals("1") && password != "") {
                if (!dao.isDelete(email) && !dao.isDelete(userID)) {
                    if (!dao.checkUserExisted(userID) && !dao.checkUserExisted(email)) {
                        if (!Validation.isValidUserID(userID)) {
                            foundErr = true;
                            error.setIdError("User ID require input 8 characters or invalid user ID pattern");
                        }
                        if (userName.trim().length() < 2 || userName.trim().length() > 30) {
                            foundErr = true;
                            error.setNameError("User name require input 2 to 30 characters");
                        }
                        if (password.trim().length() < 8 || password.trim().length() > 16) {
                            foundErr = true;
                            error.setPasswordError("Password require input 8 to 16 characters");
                        }
                        if (!Validation.isValidEmail(email.toLowerCase().trim())) {
                            foundErr = true;
                            error.setEmailError("Invalid email pattern");
                        }
                        if (phoneNumber != "") {
                            if (!Validation.isValidPhoneNumber(phoneNumber.trim())) {
                                foundErr = true;
                                error.setPhoneNumberError("Phone number require 8 numbers");
                            }
                        }
                        if (!(roleID.equals("1"))) {
                            foundErr = true;
                            error.setRoleIDError("Invalid role ID");
                        }

                        if (foundErr) {
                            request.setAttribute("CREATE_ERROR", error);
                        } else {
                            dao.addUser(userID.toUpperCase().trim(), userName.trim(), roleID, passwordHashed.trim(),
                                    email.toLowerCase().trim(), phoneNumber.trim(), "0", profilePicturePath,
                                    false, false, false, false);
                        }
                    } else {
                        request.setAttribute("ADD_DUPLICATE", "User have existed! Please check again userID or email.");
                    }
                } else {
                    request.setAttribute("DELETED_USER", "This staff have been deleted before. If you want to add again, please undelete it!");
                }
            } else if (roleID.equals("2") || roleID.equals("3")) {
                if (!dao.isDelete(email) && !dao.isDelete(userID)) {
                    if (!dao.checkUserExisted(userID) && !dao.checkUserExisted(email)) {
                        if (!Validation.isValidUserID(userID.trim())) {
                            foundErr = true;
                            error.setIdError("User ID require input 8 characters or invalid user ID pattern");
                        }
                        if (userName.trim().length() < 2 || userName.trim().length() > 30) {
                            foundErr = true;
                            error.setNameError("User name require input 2 to 30 characters");
                        }
                        if (!Validation.isValidEmail(email.trim().toLowerCase())) {
                            foundErr = true;
                            error.setEmailError("Invalid email pattern");
                        }
                        if (phoneNumber != "") {
                            if (!Validation.isValidPhoneNumber(phoneNumber.trim())) {
                                foundErr = true;
                                error.setPhoneNumberError("Phone number require 10 numbers");
                            }
                        }
                        if (!(roleID.equals("2") || roleID.equals("3"))) {
                            foundErr = true;
                            error.setRoleIDError("Invalid role ID");
                        }

                        if (foundErr) {
                            request.setAttribute("CREATE_ERROR", error);
                        } else {
                            dao.addUser(userID.toLowerCase().trim(), userName.trim(), roleID, "",
                                    email.toLowerCase().trim(), phoneNumber.trim(), "0", profilePicturePath,
                                    false, false, false, false);
                        }
                    } else {
                        request.setAttribute("ADD_DUPLICATE", "User have existed! Please check again userID or email.");
                    }
                } else {
                    request.setAttribute("DELETED_USER", "This staff have been deleted before. If you want to add again, please undelete it!");
                }
            } else {
                request.setAttribute("PASSWORD_ADMIN", "Admin must have password!");
            }

            if (searchValue == null || searchValue.trim().isEmpty()) {
                url = SHOW_STAFF_CONTROLLER;
            } else {
                url = SEARCH_STAFF_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("AddStaffServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("AddStaffServlet _ Naming: " + ex.getMessage());
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
