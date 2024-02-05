package com.gojek.client;

import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpClient {
    Logger log = LoggerFactory.getLogger(HttpClient.class);

    private String serviceResponse;
    private Boolean requestSuccess;

    public HttpClient(String requestURL) {
        try {
            serviceResponse = GetInvocationBuilder(requestURL, "json")
                    .get(String.class);
            requestSuccess = true;
        } catch (javax.ws.rs.NotFoundException nfe) {
            log.info("URL: " + requestURL + " Not Found");

            log.info(nfe.getMessage());
        } catch (javax.ws.rs.RedirectionException re) {
            log.info("Got Request Redirect for URL: " + requestURL);
            log.info(re.getMessage());
        } catch (javax.ws.rs.ProcessingException pe) {
            log.info("Error Processing the Request URL: " + requestURL);
            log.info(pe.getMessage());
        } catch (Exception e) {
            log.info("Error while making request");
            log.info(e.getMessage());
        }
    }

    public String getServiceResponse() {
        return serviceResponse;
    }


    public Boolean isRequestSuccess() {
        return requestSuccess;
    }

    public Client getClient() {
        return ClientBuilder.newBuilder().sslContext(getSSLContext()).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();
    }


    public SSLContext getSSLContext() {
        TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {

            }
        }};

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManager, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private WebTarget getWebTarget(String reuestUrl) {
        Client client = getClient();
        String url = reuestUrl;

        WebTarget webTarget = client.target(url);
        webTarget.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
        return webTarget;
    }

    private Invocation.Builder GetInvocationBuilder(String requestUrl, String mediaType) {
        WebTarget target = getWebTarget(requestUrl);
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

        switch (getAccept(mediaType)) {

            case XML:
                builder = target.request(MediaType.APPLICATION_XML);
                break;
            case JSON:
                builder = target.request(MediaType.APPLICATION_JSON);
                break;
        }
        return builder;
    }


    private AcceptType getAccept(String type) {
        if (type == null) {
            return AcceptType.valueOf("json");
        }
        return AcceptType.valueOf(type.trim().toUpperCase());
    }

}
