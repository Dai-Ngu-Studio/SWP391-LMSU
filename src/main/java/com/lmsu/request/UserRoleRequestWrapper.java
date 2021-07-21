package com.lmsu.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;
import java.util.List;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

    private String user;
    private String role = null;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String user, String role, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.role = role;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.role == null) {
            return this.realRequest.isUserInRole(role);
        }
        return this.role.equals(role);
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }

        // Make an anonymous implementation to just return our user
        return new Principal() {
            @Override
            public String getName() {
                return user;
            }
        };
    }
}