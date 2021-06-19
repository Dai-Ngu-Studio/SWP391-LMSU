package com.lmsu.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringHelpers {

    private static final int STRING_LENGTH = 20;
    private static final boolean USE_LETTERS = true;
    private static final boolean USE_NUMBER = true;

    public String generatingRandomString() {
        String generatedString = RandomStringUtils.random(STRING_LENGTH, USE_LETTERS, USE_NUMBER);
        return generatedString;
    }
}
