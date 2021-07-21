package com.lmsu.config;

import java.util.*;

public class SecurityConfig {

    public static final String ROLE_MANAGER = "2";
    public static final String ROLE_LIBRARIAN = "3";
    public static final String ROLE_ADMIN = "1";
    public static final String ROLE_MEMBER = "4";
    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

    static {
        init();
    }

    private static void init() {

        // Cấu hình cho vai trò "LIB".
        List<String> urlPatterns1 = new ArrayList<String>();

        urlPatterns1.add("/ShowOrdersServlet");
        urlPatterns1.add("/ShowProfileServlet");
        urlPatterns1.add("/dashboard.jsp");
        urlPatterns1.add("/ShowAnnouncementManagementServlet");
        urlPatterns1.add("/ShowRenewalRequestsServlet");
        urlPatterns1.add("/ShowPenaltiesServlet");
        mapConfig.put(ROLE_LIBRARIAN, urlPatterns1);

        // Cấu hình cho vai trò "MANAGER".
        List<String> urlPatterns2 = new ArrayList<String>();

        urlPatterns2.add("/ShowBookServlet");
        urlPatterns2.add("/ShowOrdersServlet");
        urlPatterns2.add("/ShowProfileServlet");
        urlPatterns2.add("/dashboard.jsp");
        urlPatterns2.add("/ShowAuthorServlet");
        urlPatterns2.add("/ShowRenewalRequestsServlet");
        urlPatterns2.add("/ShowPenaltiesServlet");
        urlPatterns2.add("/ShowSubjectServlet");
        mapConfig.put(ROLE_MANAGER, urlPatterns2);

        // Cấu hình cho vai trò "MEM".
        List<String> urlPatterns3 = new ArrayList<String>();

        urlPatterns3.add("/ShowProfileServlet");

        mapConfig.put(ROLE_MEMBER, urlPatterns3);

        // Cấu hình cho vai trò "ADM".
        List<String> urlPatterns4 = new ArrayList<String>();

        urlPatterns4.add("/ShowOrdersServlet");
        urlPatterns4.add("/ShowProfileServlet");
        urlPatterns4.add("/dashboard.jsp");
        urlPatterns4.add("/ShowAnnouncementManagementServlet");
        urlPatterns4.add("/ShowRenewalRequestsServlet");
        urlPatterns4.add("/ShowPenaltiesServlet");
        urlPatterns4.add("/ShowBookServlet");
        urlPatterns4.add("/ShowAuthorServlet");
        urlPatterns4.add("/ShowSubjectServlet");
        urlPatterns4.add("/ShowMemberServlet");
        urlPatterns4.add("/ShowStaffServlet");
        urlPatterns4.add("/ShowLogServlet");

        mapConfig.put(ROLE_MEMBER, urlPatterns4);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}
