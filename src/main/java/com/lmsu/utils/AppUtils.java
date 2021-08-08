package com.lmsu.utils;

import com.lmsu.users.UserDTO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AppUtils {

    private static final Map<Integer, String> id_uri_map = new HashMap<Integer, String>();
    private static final Map<String, Integer> uri_id_map = new HashMap<String, Integer>();
    private static int REDIRECT_ID = 0;

    // Lưu trữ thông tin người dùng vào Session.
    public static void storeLoginedUser(HttpSession session, UserDTO loginedUser) {
        // Trên JSP có thể truy cập thông qua ${loginedUser}
        session.setAttribute("LOGIN_USER", loginedUser);
    }

    // Lấy thông tin người dùng lưu trữ trong Session.
    public static UserDTO getLoginedUser(HttpSession session) {
        UserDTO loginedUser = (UserDTO) session.getAttribute("LOGIN_USER");
        return loginedUser;
    }

    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = uri_id_map.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = id_uri_map.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }

}