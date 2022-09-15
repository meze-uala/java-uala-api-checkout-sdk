package main.client;

import com.google.gson.Gson;
import main.config.Config;
import main.config.Environment;
import main.dto.*;

import java.io.*;
import java.net.HttpURLConnection; 
import java.net.URL;

public class Client {

    private Config config;
    private Gson encoder;

    public Client(Config config) {
        this.config = config;
        this.encoder = new Gson();
    }

    public Client(){
        String environment = System.getProperty("ENVIRONMENT");
        Config config;
        this.encoder = new Gson();
        if(environment != null && (environment.toLowerCase().equals(Environment.STAGE.getEnvironment()) ||
                environment.toLowerCase().equals(Environment.PRODUCTION.getEnvironment()))){
            config = new Config(environment.toLowerCase());
            this.config = config;
        } else {
            Config defaultConfig = new Config("STAGE");
            this.config = defaultConfig;
        }
    }

    public Client(String environment){
        if(environment.toLowerCase().equals(Environment.STAGE.getEnvironment())){
            Config config = new Config(Environment.STAGE.getEnvironment());
            this.config = config;
        } else if (environment.toLowerCase().equals(Environment.PRODUCTION.getEnvironment())){
            Config config = new Config(Environment.PRODUCTION.getEnvironment());
            this.config = config;
        } else {
            Config config = new Config(Environment.STAGE.getEnvironment());
            this.config = config;
        }
    }

    public Config getConfig() {
        return config;
    }

    public Gson getEncoder() {
        return encoder;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getAuthToken(AuthRequest request) throws IOException {

        if (!request.validRequest()){
            return invalidAuthRequest();
        }

        URL url = new URL(String.format("%s%s", config.getAuthAPIbaseUrl(), "/token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        //Gson gson = new Gson();
        String gsonRequest = getEncoder().toJson(request);


        try(OutputStream os = con.getOutputStream()) {
            byte[] input = gsonRequest.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

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
                AuthResponse authResponse = getEncoder().fromJson(content.toString(), AuthResponse.class);
                return getEncoder().toJson(authResponse);
            }
        } catch (Exception e){
            GenericResponse gr = new GenericResponse("999", "an error occurred when trying to get authToken", null, null);
            return getEncoder().toJson(gr);
        }
    }

    public String createOrder(OrderRequest or, String authToken) throws IOException {

        URL url = new URL(String.format("%s%s", config.getApiBaseUrl(), "/checkout"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", String.format("%s %s", "Bearer", authToken));
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String orderReqJson = getEncoder().toJson(or);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = orderReqJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

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
                OrderResponse orderResponse = getEncoder().fromJson(content.toString(), OrderResponse.class);
                return getEncoder().toJson(orderResponse);
            }
        } catch (Exception e){
            GenericResponse gr = new GenericResponse("999", "an error occurred when trying to get authToken", null, null);
            return getEncoder().toJson(gr);
        }

    }

    //GetOrder
    public String getOrder(String orderID, String authToken) throws IOException {
        URL url = new URL(String.format("%s%s%s", config.getApiBaseUrl(), "/order/", orderID));
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
                GetOrderResponse getOrderResponse = getEncoder().fromJson(content.toString(), GetOrderResponse.class);
                return getEncoder().toJson(getOrderResponse);
            }
        } catch (Exception e){
            GenericResponse gr = new GenericResponse("999", "an error occurred when trying to get authToken", null, null);
            return getEncoder().toJson(gr);
        }
    }

    public String invalidAuthRequest(){
        GenericResponse response = new GenericResponse("3001", "One or more required fields are empty", null, null);
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
