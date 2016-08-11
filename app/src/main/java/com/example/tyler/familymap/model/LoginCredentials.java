package com.example.tyler.familymap.model;

import java.util.UUID;

/**
 * Created by Tyler on 7/26/2016.
 */
public class LoginCredentials {

    private UUID mId;
    private String mUsername;
    private String mPassword;
    private String mServerHost;
    private String mServerPort;
    private String authToken;


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmServerHost() {
        return mServerHost;
    }

    public void setmServerHost(String mServerHost) {
        this.mServerHost = mServerHost;
    }

    public String getmServerPort() {
        return mServerPort;
    }

    public void setmServerPort(String mServerPort) {
        this.mServerPort = mServerPort;
    }
}
