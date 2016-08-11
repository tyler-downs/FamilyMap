package com.example.tyler.familymap.communication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tyler on 7/27/2016.
 */
public class FamilyMapProxy extends HTTPServerProxy {


    private Map<String, String> headers;
    private Context context;
    private String postData;

    public class NetworkNotAvailableException extends Exception {}

    public FamilyMapProxy(String host, String port, Context context) {
        super(host, port);
        this.context = context;
        headers = new HashMap<>();
    }

    public void login(String username, String password, IProxyDelegate delegate) {
        String urlExt = "/user/login";
        postData = "{\n" +
                "\tusername:\"" + username + "\",\n" +
                "\tpassword:\"" + password + "\"\n" +
                "}";
        POSTTask postTask = new POSTTask();
        postTask.execute(urlExt, headers, delegate);
    }

    public void getFirstLastName(String authToken, String personID, IProxyDelegate delegate)
    {
        String urlExt = "/person/" + personID;
        headers.put("Authorization", authToken);
        postData = "";
        POSTTask postTask = new POSTTask();
        postTask.execute(urlExt, headers, delegate);
    }

    /**
     * Used to get all people info from the server
     * @param authToken
     * @param delegate
     */
    public void getPeople(String authToken, IProxyDelegate delegate)
    {
        String urlExt = "/person/";
        headers.put("Authorization", authToken);
        postData = "";
        POSTTask postTask = new POSTTask();
        postTask.execute(urlExt, headers, delegate);
    }

    public void getEvents(String authToken, IProxyDelegate delegate)
    {
        String urlExt = "/event/";
        postData = "";
        POSTTask postTask = new POSTTask();
        postTask.execute(urlExt, headers, delegate);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class GETTask extends AsyncTask<Object, Integer, ProxyResult> {

        private IProxyDelegate delegate;

        @Override
        protected ProxyResult doInBackground(Object... params) {

            delegate = (IProxyDelegate)params[2];
            try {
                return GET((String)params[0], (Map<String,String>)params[1]);
            } catch(MalformedURLException e) {
                return new ProxyResult(true, e);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {

        }

        @Override
        protected void onPostExecute(ProxyResult result) {
            delegate.onProxyResult(result);
        }

    }

    private class POSTTask extends AsyncTask<Object, Integer, ProxyResult>{

        private IProxyDelegate delegate;

        @Override
        protected ProxyResult doInBackground(Object... params) {
            delegate = (IProxyDelegate)params[2];
            return POST((String)params[0], postData, (Map<String,String>)params[1]);
        }


        @Override
        protected void onProgressUpdate(Integer... integers) {

        }

        @Override
        protected void onPostExecute(ProxyResult result) {
            delegate.onProxyResult(result);
        }
    }
}
