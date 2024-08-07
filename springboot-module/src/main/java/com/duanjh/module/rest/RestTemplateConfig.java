package com.duanjh.module.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 单位毫秒
      */
    static final int TIMEOUT = 60000;

    @Bean
    public RestTemplate restTemplate(ClientHttpsRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpsRequestFactory simpleClientHttpRequestFactory() {
        ClientHttpsRequestFactory factory = new ClientHttpsRequestFactory();
        factory.setReadTimeout(TIMEOUT);
        factory.setConnectTimeout(TIMEOUT);
        return factory;
    }

    /**
     * 跳过证书验证封装
     */
    static class ClientHttpsRequestFactory extends SimpleClientHttpRequestFactory {

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                prepareHttpsConnection((HttpsURLConnection) connection);
            }
            super.prepareConnection(connection, httpMethod);
        }

        private void prepareHttpsConnection(HttpsURLConnection connection) {
            connection.setHostnameVerifier(new SkipHostnameVerifier());
            try {
                connection.setSSLSocketFactory(createSslSocketFactory());
            } catch (Exception ex) {
                // Ignore
            }
        }

        private SSLSocketFactory createSslSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] var1, String var2) {
                    //write what you want to handler
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] var1, String var2) {
                    //write what you want to handler
                }

            } }, new java.security.SecureRandom());
            return context.getSocketFactory();
        }

        private static class SkipHostnameVerifier implements HostnameVerifier {

            @Override
            public boolean verify(String requestedHost, SSLSession remoteServerSession) {
                return requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()); // Compliant
            }
        }
    }
}
