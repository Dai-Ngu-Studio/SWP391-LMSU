package com.lmsu.controller.member;

import com.lmsu.users.UpdateUserErrorDTO;
import com.lmsu.users.UserDAO;
import com.lmsu.utils.ImageHelpers;
import com.lmsu.validation.Validation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateUserServlet", value = "/UpdateUserServlet")
public class UpdateMemberServlet extends HttpServlet {

    private final String SEARCH_MEMBER_CONTROLLER = "SearchMemberServlet";
    private final String SHOW_MEMBER_CONTROLLER = "ShowMemberServlet";
    static final Logger LOGGER = Logger.getLogger(UpdateMemberServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = SHOW_MEMBER_CONTROLLER;

        String memberID = request.getParameter("userPk");
        String userName = request.getParameter("txtUpdateMemberName");
        String phoneNumber = request.getParameter("txtUpdatePhoneNumber");
        String searchVal = request.getParameter("txtSearchValue");
        String coverFile = request.getParameter("txtCoverFile");
        String activeStatus = request.getParameter("txtUpdateActiveStatus");
        String semester = request.getParameter("txtUpdateMemberSemester");
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

            if (userName.trim().length() < 2 || userName.trim().length() > 30) {
                foundErr = true;
                error.setNameError("User name require input 2 to 30 characters");
            }
            if (!phoneNumber.isEmpty()) {
                if (!Validation.isValidPhoneNumber(phoneNumber.trim())) {
                    foundErr = true;
                    error.setPhoneNumberError("Phone number require 10 numbers");
                }
            }
            if (!(activeStatus.equals("0") || activeStatus.equals("1"))) {
                foundErr = true;
                error.setRoleIDError("Invalid active status");
            }
            try {
                int semesterNo = Integer.parseInt(semester);

                if (semesterNo < 0 || semesterNo > 9) {
                    foundErr = true;
                    error.setSemesterError("Semester require input 0 to 9");
                }
            } catch (NumberFormatException ex){
                foundErr = true;
                error.setSemesterError("Semester require input 0 to 9");
            }

            if (foundErr) {
                request.setAttribute("UPDATE_ERROR", error);
            } else {
                dao.updateMember(memberID, coverFile, phoneNumber, Boolean.parseBoolean(activeStatus), userName, "4");
            }

            if (searchVal == null || searchVal.trim().isEmpty()) {
                url = SHOW_MEMBER_CONTROLLER;
            } else {
                url = SEARCH_MEMBER_CONTROLLER;
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
