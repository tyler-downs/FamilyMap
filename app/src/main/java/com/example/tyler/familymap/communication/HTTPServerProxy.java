package com.example.tyler.familymap.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Tyler on 7/27/2016.
 */
public class HTTPServerProxy {

    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    private String host;
    private String port;
    private String baseURL;

    public HTTPServerProxy(String host, String port) {
        this.host = host;
        this.port = port;
        this.baseURL = "http://" + host;
    }

    public ProxyResult GET(String urlExt, Map<String,String> headers) throws MalformedURLException {
        URL url = new URL(baseURL + urlExt);
        try {

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_GET);

            for(String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();


                return new ProxyResult(false, inputstreamToString(responseBody));
            }
            else {
                return new ProxyResult(true, new Exception("Server returned an error."));
            }

        } catch (IOException e) {
            return new ProxyResult(true, e);

        }
    }

    public ProxyResult POST(String urlExt, String postData, Map<String, String> headers) {

        try {
            URL url = new URL(baseURL + urlExt);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);

            for(String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }

            connection.connect();

            // Write post data to request body
            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(postData.getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                return new ProxyResult(false, inputstreamToString(responseBody));
            }
            else {
                return new ProxyResult(true, new Exception("Server returned an error."));
            }
        }
        catch (IOException e) {
            return new ProxyResult(true, e);
        }
    }

    private String inputstreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }
}
