package org.example.microservicesdemo.utils;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component

@ConfigurationProperties(prefix = "microservices")
public class PropertiesHelper {
    public static final String SINGLE_GET = "get";
    public static final String GET_ALL = "getAll";
    public static final String POST = "post";
    public static final String COMMON = "common";
    public static final Integer ORDERS_MS_POSITION = 0;
    public static final Integer CACHE_MS_POSITION = 1;
    private List<MicroServiceEntry> msList;


    public PropertiesHelper(List<MicroServiceEntry> msList) {
        this.msList = msList;
    }

    public List<MicroServiceEntry> getMsList() {
        return msList;
    }

    public void setMsList(List<MicroServiceEntry> msList) {
        this.msList = msList;
    }

    public String returnUrlOrders()
    {
        String baseUrl =  msList.get(ORDERS_MS_POSITION).getUrlWithPort();
        String path = msList.get(ORDERS_MS_POSITION).getCommon();
        boolean securedIndication=false;
        securedIndication = isSecuredEndpoint(baseUrl, securedIndication);
        return composeUrl(baseUrl,path,securedIndication);

    }

    public String returnUrlCaching()
    {
        String baseUrl =  msList.get(CACHE_MS_POSITION).getUrlWithPort();
        String path = msList.get(CACHE_MS_POSITION).getGet();
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

    public static class MicroServiceEntry {
        private String urlWithPort;
        private String common;
        private String get;
        private String getAll;
        private String post;



        public String getUrlWithPort() {
            return urlWithPort;
        }

        public void setUrlWithPort(String urlWithPort) {
            this.urlWithPort = urlWithPort;
        }

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String getGetAll() {
            return getAll;
        }

        public void setGetAll(String getAll) {
            this.getAll = getAll;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }
    }

}
