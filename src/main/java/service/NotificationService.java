package service;

import com.google.gson.Gson;
import config.Config;
import dto.FailedNotificationResponse;
import dto.GenericResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationService implements INotificationService {

    private Gson encoder;
    private Config config;

    public NotificationService(Config config, Gson encoder){
        this.config = config;
        this.encoder = encoder;
    }

    @Override
    public String getFailedNotifications(String authToken) throws IOException {
        URL url = new URL(String.format("%s%s", config.getApiBaseUrl(), "/notifications"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", String.format("%s %s", "Bearer", authToken));
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        int status = con.getResponseCode();

        Reader streamReader = null;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        try {
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (status > 299) {
                GenericResponse gr = getEncoder().fromJson(content.toString(), GenericResponse.class);
                return getEncoder().toJson(gr);
            } else {
                FailedNotificationResponse failedNotifResponse = getEncoder().fromJson(content.toString(), FailedNotificationResponse.class);
                return getEncoder().toJson(failedNotifResponse);
            }
        } catch (Exception e){
            throw new IOException("An exception occurred when trying to get order.");
        }
    }

    public Gson getEncoder() {
        return encoder;
    }

    public void setEncoder(Gson encoder) {
        this.encoder = encoder;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
