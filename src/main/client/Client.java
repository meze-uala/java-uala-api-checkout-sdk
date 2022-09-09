package main.client;

import main.config.Config;

public class Client {

    private Config config;

    public Client(Config config) {
        this.config = config;
    }

    public Client(){}

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
