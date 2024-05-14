package com.filesharing.filebin.responses;

import java.text.MessageFormat;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.
                format("LoginResponse={token={0}, expiresIn='{1}'}",
                        this.token,
                        this.expiresIn);
    }
}
