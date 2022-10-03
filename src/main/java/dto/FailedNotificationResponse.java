package dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FailedNotificationResponse {
    
    @SerializedName("notifications")
    private ArrayList<Notification> notifications;

    public FailedNotificationResponse(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
