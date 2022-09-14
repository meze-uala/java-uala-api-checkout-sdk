package main.dto;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("orderNumber")
    private String orderNumber;
    @SerializedName("currency")
    private String currency;
    @SerializedName("amount")
    private String amount;
    @SerializedName("status")
    private String status;
    @SerializedName("refNumber")
    private String refNumber;
    @SerializedName("link")
    private Links links;

    public OrderResponse(String id, String type, String uuid, String orderNumber, String currency, String amount, String status, String refNumber, Links links) {
        this.id = id;
        this.type = type;
        this.uuid = uuid;
        this.orderNumber = orderNumber;
        this.currency = currency;
        this.amount = amount;
        this.status = status;
        this.refNumber = refNumber;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
