package com.lmsu.controller.announcement;

import com.lmsu.announcement.AnnouncementDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.DateHelpers;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "CreateAnnouncementServlet", value = "/CreateAnnouncementServlet")
public class CreateAnnouncementServlet extends HttpServlet {

    private final String ANNOUNCEMENT_MANAGEMENT_CONTROLLER = "ShowAnnouncementManagementServlet";
    static final Logger LOGGER = Logger.getLogger(CreateAnnouncementServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String semester = request.getParameter("semester");
        String year = request.getParameter("year");
        String defaultDayFrom = request.getParameter("defaultDayFrom");
        String defaultDateFrom = request.getParameter("defaultDateFrom");
        String defaultDayTo = request.getParameter("defaultDayTo");
        String defaultDateTo = request.getParameter("defaultDateTo");
        String subject = request.getParameter("subject");
        String day = request.getParameter("day");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String returnDeadline = request.getParameter("returnDate");
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("LOGIN_USER");

        String url = ANNOUNCEMENT_MANAGEMENT_CONTROLLER;

        try {
            Date localDate = DateHelpers.getCurrentDate();
            LocalDate localDayFrom = LocalDate.parse(defaultDateFrom);
            LocalDate localDayTo = LocalDate.parse(defaultDateTo);
            LocalDate localDay = LocalDate.parse(dateFrom);
            if (userDTO != null){
                String announcementText = "<div class=\"d-flex flex-column align-items-center mb-5\">" +
                        "<h6><b>INFORMATION AND LIBRARY CENTER</b></h6><h3><b>ANNOUNCEMENT</b></h3>" +
                        "</div><div><p>Dear Library Users," +
                        "</p><p> Delivering textbook schedule for " + semester +
                        " " + year +
                        " semester at FPT University Library. " +
                        "Library users are required to follow below schedule:</p><p class=\"text-danger\"> " +
                        "<b> Start: " + localDayFrom.getDayOfWeek() + " " + localDayFrom +
                        " to " + localDayTo.getDayOfWeek() + " " + localDayTo +
                        " at room no LB102. " +
                        "</b></p><p> Textbook for subjects: " + subject +
                        "<b class=\"text-danger\"> from " + localDay.getDayOfWeek() + " " + localDay + " to " + dateTo +
                        " at room no LB101</b> " +
                        ".</p><h3><b>Time:</b></h3><p> - Morning: From 08:30 to 11:30 <br> - Afternoon: From 13:30 to 16:30</p>" +
                        "<h3><b>Notes:</b></h3><p> - Students must return old textbooks before borrow new textbooks; " +
                        "<br> - Students who don't borrow and get textbooks as schedule must take responsibility of having no textbooks; " +
                        "<br> - Students can view information of textbooks at here; " +
                        "<br> - Students have to return their books before <b class=\"text-danger\">" + returnDeadline + "</b> </p>" +
                        "<h3><b>Should you have any inquiry, " +
                        "please contact us via:</b></h3><p> <b>Phone No:</b> 024-6680 5912 <br> <b>Email:</b> lmsu@gmail.com <br> <b>Fanpage:</b> https://www.facebook.com/lmsu</p>";
                AnnouncementDAO dao = new AnnouncementDAO();
                boolean result = dao.addAnnouncement(localDate, announcementText, userDTO.getId(), Date.valueOf(returnDeadline));
                if (result){
                    url = ANNOUNCEMENT_MANAGEMENT_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            log("CreateAnnouncementServlet _ SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
            log("CreateAnnouncementServlet _ Naming: " + ex.getMessage());
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
