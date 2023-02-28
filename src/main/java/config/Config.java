package config;

import config.*;

public class Config {

    private final String environment;
    private final String apiBaseUrl;
    private final String authAPIbaseUrl;

    public Config(String environment) {
        //TODO: mejorar con un switch, manejando el toStr del enum
        if (environment.toLowerCase().equals(Environment.STAGE.getEnvironment())){
            this.apiBaseUrl =  ApiBaseUrl.STAGE.getBaseUrl();
            this.authAPIbaseUrl = AuthApiBaseUrl.STAGE.getAuthUrl();
            this.environment = environment.toLowerCase();
        } else if (environment.toLowerCase().equals(Environment.PRODUCTION.getEnvironment())){
            this.apiBaseUrl =  ApiBaseUrl.PRODUCTION.getBaseUrl();
            this.authAPIbaseUrl =  AuthApiBaseUrl.PRODUCTION.getAuthUrl();
            this.environment = environment.toLowerCase();
        } else {
            this.apiBaseUrl =  ApiBaseUrl.STAGE.getBaseUrl();
            this.authAPIbaseUrl = AuthApiBaseUrl.STAGE.getAuthUrl();
            this.environment = Environment.STAGE.getEnvironment();
        }
    }

    public String getEnvironment() {
        return environment;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public String getAuthAPIbaseUrl() {
        return authAPIbaseUrl;
    }
}
