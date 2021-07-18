package com.lmsu.controller.announcement;

import com.lmsu.announcement.AnnouncementDAO;
import com.lmsu.announcement.AnnouncementDTO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DateHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowAnnouncementServlet", value = "/ShowAnnouncementServlet")
public class ShowAnnouncementServlet extends HttpServlet {

    private final String ANNOUNCEMENT_PAGE = "announcement.jsp";
    static final Logger LOGGER = Logger.getLogger(ShowAnnouncementServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String url = ANNOUNCEMENT_PAGE;

        try {
            AnnouncementDAO dao = new AnnouncementDAO();
            dao.viewAnnouncementList();
            List <AnnouncementDTO> result = dao.getAnnouncementList();
            if (result != null){
                request.setAttribute("ANNOUNCEMENT_LIST", result);
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAnnouncementServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("ShowAnnouncementServlet _ Naming: " + ex.getMessage());
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
