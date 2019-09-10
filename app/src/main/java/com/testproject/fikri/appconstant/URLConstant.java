package com.testproject.fikri.appconstant;

import com.testproject.fikri.service.api;

public class URLConstant {
    public static  final String BASE_URL="https://newsapi.org/";
    public static int READ_TIMEOUT = 6000;
    public static int CONNECTION_TIMEOUT = 6000;
    private static api svcApi;
    public static api getapi() {
        if (svcApi != null) {
            return svcApi;
        } else {
            svcApi = new api();
        }
        return svcApi;
    }
}
