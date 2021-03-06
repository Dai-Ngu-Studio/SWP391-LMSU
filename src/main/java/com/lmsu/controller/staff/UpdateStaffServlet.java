package com.lmsu.controller.staff;

import com.google.common.hash.Hashing;
import com.lmsu.users.UpdateUserErrorDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.utils.ImageHelpers;
import com.lmsu.validation.Validation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "UpdateStaffServlet", value = "/UpdateStaffServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UpdateStaffServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdateStaffServlet.class);
    private final String SEARCH_STAFF_CONTROLLER = "SearchStaffServlet";
    private final String SHOW_STAFF_CONTROLLER = "ShowStaffServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = SHOW_STAFF_CONTROLLER;

        String memberID = request.getParameter("userPk");
        String userName = request.getParameter("txtUpdateMemberName");
        String password = request.getParameter("txtUpdateMemberPassword");
        String passwordHashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        String phoneNumber = request.getParameter("txtUpdatePhoneNumber");
        String roleID = request.getParameter("txtUpdateRoleID");
        String coverFile = request.getParameter("txtCoverFile");
        String activeStatus = request.getParameter("txtUpdateActiveStatus");
        String searchVal = request.getParameter("txtSearchValue");
        boolean foundErr = false;

        try {
            UserDAO dao = new UserDAO();
            UpdateUserErrorDTO error = new UpdateUserErrorDTO();

            String uploadPath = ImageHelpers.getPathImgFolder(getServletContext().getRealPath(""));
            String fileName = "";
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();
                if (!(fileName == null || fileName.trim().isEmpty())) {
                    fileName = "member-" + memberID + "." + FilenameUtils.getExtension(fileName);
                    part.write(uploadPath + fileName);
                    coverFile = fileName;
                    break;
                }
            }

            if (userName.trim().length() < 2 || userName.trim().length() > 20) {
                foundErr = true;
                error.setNameError("User name require input 2 to 20 characters");
            }
            if (phoneNumber != "") {
                if (!Validation.isValidPhoneNumber(phoneNumber)) {
                    foundErr = true;
                    error.setPhoneNumberError("Phone number require 10 numbers");
                }
            }
            if (!(roleID.equals("1") || roleID.equals("2") || roleID.equals("3"))) {
                foundErr = true;
                error.setRoleIDError("Invalid role ID");
            }
            if (!(activeStatus.equals("0") || activeStatus.equals("1"))) {
                foundErr = true;
                error.setRoleIDError("Invalid active status");
            }
            if (foundErr) {
                request.setAttribute("UPDATE_ERROR", error);
            } else {
                if (password.isEmpty()) {
                    dao.updateMember(memberID, coverFile, phoneNumber, Boolean.parseBoolean(activeStatus), userName, roleID);
                }
                if (!password.isEmpty()) {
                    dao.updateMember(memberID, coverFile, phoneNumber, Boolean.parseBoolean(activeStatus), userName, roleID, passwordHashed);
                }
            }

            if (searchVal == null || searchVal.trim().isEmpty()) {
                url = SHOW_STAFF_CONTROLLER;
            } else {
                url = SEARCH_STAFF_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateUserServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("UpdateUserServlet _ Naming: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
