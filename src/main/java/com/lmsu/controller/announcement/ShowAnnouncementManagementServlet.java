package com.lmsu.controller.announcement;

import com.lmsu.announcement.AnnouncementDAO;
import com.lmsu.announcement.AnnouncementDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowAnnouncementManagementServlet", value = "/ShowAnnouncementManagementServlet")
public class ShowAnnouncementManagementServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ShowAnnouncementManagementServlet.class);
    private final String ANNOUNCEMENT_PAGE = "announcementmanagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ANNOUNCEMENT_PAGE;

        try {
            List<AnnouncementDTO> announcementList = (List<AnnouncementDTO>) request.getAttribute("ANNOUNCEMENT_LIST");
            if (announcementList != null) {
                request.setAttribute("ANNOUNCEMENT_MANA_LIST", announcementList);
            } else {
                AnnouncementDAO dao = new AnnouncementDAO();
                dao.viewAnnouncementList();
                List<AnnouncementDTO> result = dao.getAnnouncementList();
                request.setAttribute("ANNOUNCEMENT_MANA_LIST", result);
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAnnouncementManagementServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAnnouncementManagementServlet _ Naming: " + ex.getMessage());
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
