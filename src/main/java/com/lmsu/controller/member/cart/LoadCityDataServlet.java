package com.lmsu.controller.member.cart;

import com.lmsu.services.GhnApis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoadCityDataServlet", value = "/LoadCityDataServlet")
public class LoadCityDataServlet extends HttpServlet {

    private final String PARAM_LOADCITY = "boolLoadCity";
    private final String PARAM_LOADDISTRICT = "boolLoadDistrict";
    private final String PARAM_LOADWARD = "boolLoadWard";

    private final String PARAM_CITYID = "txtCityID";
    private final String PARAM_DISTRICTID = "txtDistrictID";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String locationList = "";
        String boolLoadCity = request.getParameter(PARAM_LOADCITY);
        String boolLoadDistrict = request.getParameter(PARAM_LOADDISTRICT);
        String boolLoadWard = request.getParameter(PARAM_LOADWARD);
        String txtCityID = request.getParameter(PARAM_CITYID);
        String txtDistrictID = request.getParameter(PARAM_DISTRICTID);
        try {
            if (boolLoadCity.equalsIgnoreCase("true")) {
                locationList = GhnApis.getProvinceList();
            } else if (boolLoadDistrict.equalsIgnoreCase("true")) {
                locationList = GhnApis.getDistrictList(txtCityID);
            } else if (boolLoadWard.equalsIgnoreCase("true")) {
                locationList = GhnApis.getWardList(txtDistrictID);
            }
        } finally {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(locationList);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRequest(request, response);
    }
}
