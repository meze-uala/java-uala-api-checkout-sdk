package main;

import main.client.Client;
import main.config.Config;

public class Main {

    public static void main(String[] args) {

        Config config = new Config("tuvieja");

        Client client = new Client(config);


    }
}
