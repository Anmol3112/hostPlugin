package com.example.plugin.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RestClient {

    public static String makeCall(String url, String method, Object body, Map<String, String> headers)
            throws MalformedURLException, IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method.trim().toUpperCase());
        headers.forEach((key, value) -> {
            connection.setRequestProperty(key, value);
        });
        // connection.setRequestProperty("Content-Type", "application/json");

        if (connection.getResponseCode() != 200)
            return "Couldn't connect to the server";
        try {
            return parseStreamToString(connection.getInputStream());
        } catch (Exception e) {
            return "Couldn't parse the data";
        }
    }

    public static String createBasicAuthToken(String username, String password) {
        return "Basic " + new String(
                Base64.getEncoder()
                        .encode((username + ":" + password).getBytes()));
    }

    public static String parseStreamToString(InputStream stream) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(stream));
        StringBuilder obj = new StringBuilder();

        String inputLine;

        while ((inputLine = sc.readLine()) != null) {
            obj.append(inputLine);
        }

        return obj.toString();
    }

    public static void AllowAllHosts() throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

    }

}
