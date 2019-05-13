package com.myinterview.aws.util;

import org.apache.commons.validator.UrlValidator;

/**
 * Created by QArt on 2019/5/11.
 */
public class URLValidator {
    private URLValidator() {
    }

    public static boolean validateURL(String url) {
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(url);

    }
}
