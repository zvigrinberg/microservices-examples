package org.example.microservicesdemo.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "microservices")
public class PropertiesHelper {
    public static final String SINGLE_GET = "get";
    public static final String GET_ALL = "getAll";
    public static final String POST = "post";
    public static final String COMMON = "common";
    private Integer ordersPosition;
    private Integer cachingPosition;
    private List<MicroServiceEntry> msList;

    public String returnUrlOrders()
    {
        String baseUrl =  msList.get(ordersPosition).getUrlWithPort();
        String path = msList.get(ordersPosition).getEndpoints().get(COMMON);
        boolean securedIndication=false;
        securedIndication = isSecuredEndpoint(baseUrl, securedIndication);
        return composeUrl(baseUrl,path,securedIndication);

    }

    public String returnUrlCaching()
    {
        String baseUrl =  msList.get(cachingPosition).getUrlWithPort();
        String path = msList.get(ordersPosition).getEndpoints().get(SINGLE_GET);
        boolean securedIndication=false;
        securedIndication = isSecuredEndpoint(baseUrl, securedIndication);

        return composeUrl(baseUrl,path,securedIndication);

    }

    private boolean isSecuredEndpoint(String baseUrl, boolean securedIndication) {
        if(baseUrl.endsWith("443") || baseUrl.endsWith("8443"))
        {
            securedIndication =true;
        }
        return securedIndication;
    }

    private String composeUrl(String baseUrl, String path,boolean isSecured) {
        StringBuilder fullUrl = new StringBuilder();
        if(isSecured)
            fullUrl.append("https://");
        else
            fullUrl.append("http://");

        fullUrl.append(baseUrl);
        fullUrl.append(path);
        return fullUrl.toString();
    }

}
