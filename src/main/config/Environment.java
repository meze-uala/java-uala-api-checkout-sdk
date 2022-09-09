package main.config;

public enum Environment {

    STAGE("stage"),
    PRODUCTION("production");

    private final String environment;

    Environment(String env) {
        this.environment = env;
    }

    public String getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return this.environment;
    }
}
