package dto;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("account_id")
    private String accountID;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("attempts")
    private int attempts;
    @SerializedName("amount")
    private double amount;
    @SerializedName("created_date")
    private String createdDate;


    public Notification(String uuid, String accountID, int statusCode, int attempts, double amount, String createdDate) {
        this.uuid = uuid;
        this.accountID = accountID;
        this.statusCode = statusCode;
        this.attempts = attempts;
        this.amount = amount;
        this.createdDate = createdDate;
    }


    public Notification() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
