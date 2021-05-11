package com.canhlabs.shorten.share;

/**
 * Hold all constant for application
 */
public class AppConstant {
    private AppConstant() {
    }

    // Common
    public static final String BASE_DOMAIN = "https://canh-labs.com/";
    public static final int EXPIRED_DURATION = 7;
    // the constant will get all configure from application properties
    // because properties can use for all class (int Spring context and not Spring context),
    // so we need create the constant to load it
    public static final AppProperties props = new AppProperties();

    public static class API {
        private API() {
        }
        public static final String BASE_URL = "v1/shorten";
        public static final String TAG_SHORTEN = "Shorten API";
    }
}
