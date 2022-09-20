package config;

public enum AuthApiBaseUrl {
    STAGE("https://auth.stage.ua.la/1/auth"),
    PRODUCTION("https://auth.prod.ua.la/1/auth");

    private final String authUrl;

    AuthApiBaseUrl(String url) {
        this.authUrl = url;
    }

    public String getAuthUrl() {
        return authUrl;
    }
}
