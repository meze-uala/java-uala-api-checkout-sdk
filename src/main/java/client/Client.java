package client;

import com.google.gson.Gson;
import config.*;
import dto.*;
import service.AuthService;
import service.OrderService;

import java.io.*;

public class Client {

    private Config config;
    private Gson encoder;
    private AuthService authService;
    private OrderService orderService;

    public Client(Config config) {
        this.config = config;
        this.encoder = new Gson();
        this.authService = new AuthService(config,  this.getEncoder());
        this.orderService = new OrderService(config, this.getEncoder());
    }

    /**
     * This constructor allows to choose the client's environment from UalaBis
     * @param isDev define environment for client instance. Default value is prod
     */
    public Client(boolean isDev){
        if (isDev) {
            this.config = new Config(Environment.STAGE.getEnvironment());
        } else {
            this.config = new Config(Environment.PRODUCTION.getEnvironment());
        }
        this.encoder = new Gson();
        this.authService = new AuthService(config,  this.getEncoder());
        this.orderService = new OrderService(config, this.getEncoder());
    }

    /**
     * This constructor returns a Production environment Client from UalaBis
     */
    public Client() {
        this.config = new Config(Environment.PRODUCTION.getEnvironment());
        this.encoder = new Gson();
        this.authService = new AuthService(config,  this.getEncoder());
        this.orderService = new OrderService(config, this.getEncoder());
    }



    public String getAuthToken(AuthRequest request) throws IOException {
        return this.authService.getAuthToken(request);
    }

    public String createOrder(OrderRequest or, String authToken) throws IOException {
        return this.orderService.createOrder(or, authToken);
    }

    //GetOrder
    public String getOrder(String orderID, String authToken) throws IOException {
        return this.orderService.getOrder(orderID, authToken);
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

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
