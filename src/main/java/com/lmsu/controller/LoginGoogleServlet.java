package com.lmsu.controller;

import com.google.common.hash.Hashing;
import com.lmsu.accessgoogle.GooglePojo;
import com.lmsu.accessgoogle.GoogleUtils;
import com.lmsu.users.UserDAO;
import com.lmsu.users.UserDTO;
import com.lmsu.utils.AppUtils;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final String REDIRECT_LOGIN_GOOGLE_PAGE = "redirectlogingoogle.jsp";
    static final Logger LOGGER = Logger.getLogger(LoginGoogleServlet.class);

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = REDIRECT_LOGIN_GOOGLE_PAGE;

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

            String email = googlePojo.getEmail();
            String id = googlePojo.getId();
            String hashCode = "LMSU";
            String passwordHashed = Hashing.sha256()
                    .hashString(id + hashCode, StandardCharsets.UTF_8).toString();
            String profilePicture = googlePojo.getPicture();

            String isFPT = email.split("@")[1];
            try {
                UserDAO dao = new UserDAO();
                HttpSession session = request.getSession();

                if (!dao.isDelete(email)) {
                    if (isFPT.equalsIgnoreCase("fpt.edu.vn") || isFPT.equalsIgnoreCase("fe.edu.vn")) {
                        boolean isActive = dao.isActive(email);
                        if (!isActive) {
                            dao.updateOnFirstLogin(email, passwordHashed, profilePicture);
                            UserDTO dto = dao.checkLogin(email, passwordHashed);
                            if (dto != null) {
                                session.setAttribute("LOGIN_USER", dto);
                            }
                        } else {
                            UserDTO dto = dao.checkLogin(email, passwordHashed);
                            if (dto.getProfilePicturePath().split(":")[0].equalsIgnoreCase("https")){
                                if (dto != null) {
                                    AppUtils.storeLoginedUser(session, dto);
                                    dao.updateProfilePictureOnLogin(email, profilePicture);
                                } else {
                                    session.setAttribute("WRONG_USER_LOGIN", "Your account is not allowed to log into the system");
                                }
                            } else {
                                if (dto != null) {
                                    AppUtils.storeLoginedUser(session, dto);
                                } else {
                                    session.setAttribute("WRONG_USER_LOGIN", "Your account is not allowed to log into the system");
                                }
                            }
                        }
                    } else {
                        session.setAttribute("WRONG_USER_LOGIN", "Your account is not allowed to log into the system");
                    }
                } else {
                    session.setAttribute("DELETED_ACCOUNT",
                            "Your account have been removed from system. Any mistake please send us feedback.");
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                log("LoginGoogleServlet _ SQL: " + e.getMessage());
            } catch (NamingException e) {
                LOGGER.error(e.getMessage());
                log("LoginGoogleServlet _ Naming: " + e.getMessage());
            } finally {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
