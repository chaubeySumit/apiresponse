package com.gojek.client;

public class BaseUrlHelper {
    private HttpClient httpClientHelper;

    public String getResponseFromRestEndpoint(String endpoint) {
        httpClientHelper = new HttpClient(endpoint);
        return httpClientHelper.getServiceResponse();
    }

}
