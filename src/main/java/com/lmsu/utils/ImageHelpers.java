package com.lmsu.utils;

import java.io.File;

public class ImageHelpers {
    public static String getPathImgFolder(String MeoMeoPath){
//        String MeoMeoPath = getServletContext().getRealPath("");
        String partOfPath[] = MeoMeoPath.split("\\\\");
        String uploadPath = "";
        //Simple String process
        //Only get the part before the last two backslash because we don't want
        //to save it into webapp which will be get deleted every time we redeploy
        for (int i = 0; i < partOfPath.length - 2; i++) {
            uploadPath += (partOfPath[i] + File.separator);
        }
        uploadPath += ("images" + File.separator);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return uploadPath;
    }

    public static String getPathFeedbackImgFolder(String MeoMeoPath){
        String partOfPath[] = MeoMeoPath.split("\\\\");
        String uploadPath = "";
        //Simple String process
        //Only get the part before the last two backslash because we don't want
        //to save it into webapp which will be get deleted every time we redeploy
        for (int i = 0; i < partOfPath.length - 2; i++) {
            uploadPath += (partOfPath[i] + File.separator);
        }
        uploadPath += ("feedback" + File.separator);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return uploadPath;
    }
}
