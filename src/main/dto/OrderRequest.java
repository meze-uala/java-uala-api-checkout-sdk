package main.dto;

import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("amount")
    private String amount;
    @SerializedName("description")
    private String description;
    @SerializedName("userName")
    private String username;
    @SerializedName("callback_fail")
    private String callbackFail;
    @SerializedName("callback_success")
    private String callbackSuccess;
    @SerializedName("notification_url")
    private String notificationURL;
    @SerializedName("origin")
    private String origin;


    public OrderRequest(String amount, String description, String username, String callbackFail, String callbackSuccess, String notificationURL, String origin) {
        this.amount = amount;
        this.description = description;
        this.username = username;
        this.callbackFail = callbackFail;
        this.callbackSuccess = callbackSuccess;
        this.notificationURL = notificationURL;
        this.origin = origin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCallbackFail() {
        return callbackFail;
    }

    public void setCallbackFail(String callbackFail) {
        this.callbackFail = callbackFail;
    }

    public String getCallbackSuccess() {
        return callbackSuccess;
    }

    public void setCallbackSuccess(String callbackSuccess) {
        this.callbackSuccess = callbackSuccess;
    }

    public String getNotificationURL() {
        return notificationURL;
    }

    public void setNotificationURL(String notificationURL) {
        this.notificationURL = notificationURL;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
