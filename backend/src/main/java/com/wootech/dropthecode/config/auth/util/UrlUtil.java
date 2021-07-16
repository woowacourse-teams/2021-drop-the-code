package com.wootech.dropthecode.config.auth.util;

public class UrlUtil {

    private UrlUtil() {
    }

    public static String buildFullRequestUrl(String scheme, String serverName,
                                             int serverPort, String requestURI, String queryString) {

        scheme = scheme.toLowerCase();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ("http".equals(scheme) && serverPort != 80) {
            url.append(":").append(serverPort);
        }

        if ("https".equals(scheme) && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(requestURI);

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }
}
