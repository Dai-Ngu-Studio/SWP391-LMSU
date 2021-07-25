package com.lmsu.controller.member;

import com.lmsu.users.UserDAO;
import com.lmsu.utils.ImageHelpers;
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
        String memberName = request.getParameter("txtUpdateMemberName");
        String memberPhone = request.getParameter("txtUpdatePhoneNumber");
        String searchVal = request.getParameter("txtSearchValue");
        String coverFile = request.getParameter("txtCoverFile");
        boolean activeStatus = request.getParameter("txtUpdateActiveStatus").equalsIgnoreCase("0") ? false : true;
        try {
            UserDAO dao = new UserDAO();
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
            boolean result = dao.updateMember(memberID, coverFile, memberPhone, activeStatus, memberName, "4");
            if (result) {
                if (searchVal == null || searchVal.trim().isEmpty()) {
                    url = SHOW_MEMBER_CONTROLLER;
                } else {
                    url = SEARCH_MEMBER_CONTROLLER;
                }
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
