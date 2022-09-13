package main.dto;

public class AuthRequest {
    private String user_name;
    private String client_id;
    private String client_secret_id;
    private String grant_type;


    public AuthRequest(String username, String clientID, String clientSecretID) {
        this.user_name = username;
        this.client_id = clientID;
        this.client_secret_id = clientSecretID;
        this.grant_type = "client_credentials";
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret_id() {
        return client_secret_id;
    }

    public void setClient_secret_id(String client_secret_id) {
        this.client_secret_id = client_secret_id;
    }

    public String getGrantType() {
        return grant_type;
    }

    public boolean validRequest(){
        return this.getUser_name()!="" && this.getUser_name()!=null && this.getClient_id() != "" && this.getClient_id() != null
                && this.getClient_secret_id() != "" && this.getClient_secret_id() != null && this.getGrantType().equals("client_credentials");
    }
}
