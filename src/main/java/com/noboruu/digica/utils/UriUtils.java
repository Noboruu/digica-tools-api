package com.noboruu.digica.utils;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

public class UriUtils {

    /**
     * Creates a URI from a base URL and a map of query parameters without double-encoding.
     * The parameters should already be encoded if they contain special characters.
     *
     * @param baseUrl     the base URL string
     * @param queryParams a map of query parameters
     * @return a java.net.URI object
     */
    public static URI createUriWithoutEncoding(String baseUrl, Map<String, String> queryParams) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (!queryParams.isEmpty()) {
            urlBuilder.append("?");
            String queryString = queryParams.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
            urlBuilder.append(queryString);
        }
        return URI.create(urlBuilder.toString());
    }
}
