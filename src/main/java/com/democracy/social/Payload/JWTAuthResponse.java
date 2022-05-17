package com.democracy.social.Payload;

public class JWTAuthResponse {
    private String accessToken;

    private String username;
    private String tokenType="Bearer";

    public JWTAuthResponse(String accessToken,String username){
        this.accessToken=accessToken;
        this.username=username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
