package service;

import com.google.gson.Gson;
import config.Config;
import dto.GenericResponse;
import dto.GetOrderResponse;
import dto.OrderRequest;
import dto.OrderResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderService implements IOrderService{

    private Config config;
    private Gson encoder;

    public OrderService(Config config, Gson encoder){
        this.config = config;
        this.encoder = encoder;
    }

    @Override
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
             throw new IOException("An exception occurred when trying to create order.");
        }
    }

    @Override
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
            throw new IOException("An exception occurred when trying to get order.");
        }
    }


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Gson getEncoder() {
        return encoder;
    }

    public void setEncoder(Gson encoder) {
        this.encoder = encoder;
    }
}
