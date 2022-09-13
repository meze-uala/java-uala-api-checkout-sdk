package main.client;

import com.google.gson.Gson;
import main.config.Config;
import main.config.Environment;
import main.dto.AuthRequest;
import main.dto.AuthResponse;
import main.dto.GenericResponse;
import main.dto.OrderRequest;

import java.io.*;
import java.net.HttpURLConnection; 
import java.net.URL;

public class Client {

    private Config config;

    public Client(Config config) {
        this.config = config;
    }

    public Client(){
        String environment = System.getenv("ENVIRONMENT");
        Config config;
        if(environment != null && (environment.toLowerCase().equals(Environment.STAGE.getEnvironment()) ||
                environment.toLowerCase().equals(Environment.PRODUCTION.getEnvironment()))){
            config = new Config(environment.toLowerCase());
            this.config = config;
        } else {
            Config defaultConfig = new Config("STAGE");
            this.config = defaultConfig;
        }
    }

    public Config getConfig() {
        return config;
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

        Gson gson = new Gson();
        String gsonRequest = gson.toJson(request);


        try(OutputStream os = con.getOutputStream()) {
            byte[] input = gsonRequest.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();

        Reader streamReader = null;
        //
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
                GenericResponse gr = gson.fromJson(content.toString(), GenericResponse.class);
                return gson.toJson(gr);
            } else {
                AuthResponse authResponse = gson.fromJson(content.toString(), AuthResponse.class);
                return gson.toJson(authResponse);
            }
        } catch (Exception e){
            GenericResponse gr = new GenericResponse("999", "an error occurred when trying to get authToken");
            return gson.toJson(gr);
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


        //TODO ver si no hay una forma nativa mas linda de hacer este request... (sin utilizar jackson o gson)
        String orderReqStr = "{" + '"'+"amount"+'"' +":"+'"'+or.getAmount()+ '"'+ "," +
                '"'+ "description"+'"' +":"+'"'+or.getDescription()+'"'+","+
                '"'+ "userName" +'"'+":"+'"'+or.getUsername()+'"'+ ","+
                '"'+"callback_fail" + '"'+":"+'"'+or.getCallbackFail() +'"'+ ","+
                '"'+ "callback_success" +'"'+":"+'"'+or.getCallbackSuccess()+'"'+ ","+
                '"'+ "notification_url" +'"'+":"+'"'+or.getNotificationURL()+'"'+ ","+
                '"'+ "origin" +'"'+":"+'"'+or.getOrigin()+'"'+
                "}";


        try(OutputStream os = con.getOutputStream()) {
            byte[] input = orderReqStr.getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (Exception e) {
            //TODO ver el response en caso de fail. Actualmente ante una falta de user por ej, retorna esto y no lo mismo q por otro Cliente
            return "{"+'"'+"message"+'"'+":"+'"'+"an error occurred when trying to create order"+'"'+"}";
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

    /**
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
    **/

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (Exception e) {
            //TODO ver el response en caso de fail. Actualmente ante una falta de user por ej, retorna esto y no lo mismo q por otro Cliente
            return "{"+'"'+"message"+'"'+":"+'"'+"an error occurred when trying to create order"+'"'+"}";
        }
    }

    public String invalidAuthRequest(){
        GenericResponse response = new GenericResponse("3001", "One or more required fields are empty");
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
