package main.dto;

public class AuthRequest {
    private String username;
    private String clientID;
    private String clientSecretID;
    //Hardcodear
    private String grantType;


    public AuthRequest(String username, String clientID, String clientSecretID, String grantType) {
        this.username = username;
        this.clientID = clientID;
        this.clientSecretID = clientSecretID;
        this.grantType = grantType;
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

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
    //TODO PROBAR ESTO
    public boolean validRequest(){
        return this.getUsername()!="" && this.getUsername()!=null && this.getClientID() != "" && this.getClientID() != null
                && this.getClientSecretID() != "" && this.getClientSecretID() != null && this.getGrantType().equals("client_credentials");
    }
}
