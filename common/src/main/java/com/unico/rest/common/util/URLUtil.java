package com.unico.rest.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by baybora on 2/3/18.
 */
@Component
public class URLUtil {

    @Value("${common.default.hostname}")
    private String defaultHostName;

    @Value("${common.default.protocol}")
    private String defaultProtocol;


    public String createEndpoint(int localServerPort, String endpointURL){
        String url = new StringBuilder(defaultProtocol)
                .append("://")
                .append(defaultHostName)
                .append(":")
                .append(localServerPort)
                .append(endpointURL).toString();

        return url;
    }

}
