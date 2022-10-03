package service;

import dto.OrderRequest;

import java.io.IOException;

public interface INotificationService {
    String getFailedNotifications(String authToken) throws IOException;
}
