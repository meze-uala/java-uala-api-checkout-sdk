package main.config;

public enum ApiBaseUrl {
    STAGE("https://checkout.stage.ua.la/1"),
    PRODUCTION("https://checkout.prod.ua.la/1");

    private final String baseUrl;

    ApiBaseUrl(String url) {
        this.baseUrl = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
