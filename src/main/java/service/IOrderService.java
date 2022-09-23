package service;

import dto.OrderRequest;

import java.io.IOException;

public interface IOrderService {
    String createOrder(OrderRequest or, String authToken) throws IOException;
    String getOrder(String orderID, String authToken) throws IOException;
}
