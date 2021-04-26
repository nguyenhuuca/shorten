package com.canhlabs.shorten.share;

/**
 * Hold all constant for application
 */
public class AppConstant {
    private AppConstant() {
    }

    // Common
    public static final String BASE_DOMAIN = "https://canh-labs.com/";
    public static final String PREFIX_REDIRECT = "r";

    public static class API {
        private API() {
        }
        public static final String BASE_URL = "v1/shorten";
        public static final String TAG_SHORTEN = "Shorten API";
    }
}
