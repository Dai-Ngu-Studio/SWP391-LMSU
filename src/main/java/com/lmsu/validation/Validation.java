package com.lmsu.validation;

import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        return p.matcher(email).find();
    }

    public static boolean isValidPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^[0-9]{10}$");
        return p.matcher(phone).find();
    }

    public static boolean isValidUserID(String userID) {
        Pattern p = Pattern.compile("^[a-zA-Z]{2,3}[0-9]{5,6}$");
        return p.matcher(userID).find();
    }
}
