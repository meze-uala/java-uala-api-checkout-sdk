package main.dto;

import com.google.gson.annotations.SerializedName;

public class GetOrderResponse {
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("status")
    private String status;
    @SerializedName("ref_number")
    private String refNumber;
    @SerializedName("amount")
    private Double amount;

    public GetOrderResponse(String orderId, String status, String refNumber, Double amount) {
        this.orderId = orderId;
        this.status = status;
        this.refNumber = refNumber;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
