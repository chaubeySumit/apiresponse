package com.gojek.validator;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValid {
    public boolean validateUrl(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

}
