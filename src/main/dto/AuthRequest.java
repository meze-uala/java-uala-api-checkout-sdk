package main.dto;

import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("user_name")
    private String username;
    @SerializedName("client_id")
    private String clientID;
    @SerializedName("client_secret_id")
    private String clientSecretID;
    @SerializedName("grant_type")
    private String grantType;


    public AuthRequest(String username, String clientID, String clientSecretID) {
        this.username = username;
        this.clientID = clientID;
        this.clientSecretID = clientSecretID;
        this.grantType = "client_credentials";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecretID() {
        return clientSecretID;
    }

    public void setClientSecretID(String clientSecretID) {
        this.clientSecretID = clientSecretID;
    }

    public String getGrantType() {
        return grantType;
    }

    public boolean validRequest(){
        return this.getUsername()!="" && this.getUsername()!=null && this.getClientID() != "" && this.getClientID() != null
                && this.getClientSecretID() != "" && this.getClientSecretID() != null && this.getGrantType().equals("client_credentials");
    }
}
