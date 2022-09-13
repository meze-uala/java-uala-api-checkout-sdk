package main.dto;

import com.google.gson.annotations.SerializedName;

public class Links {
    @SerializedName("checkoutLink")
    private String checkoutLink;
    @SerializedName("success")
    private String success;
    @SerializedName("failed")
    private String failed;

    public Links(String checkoutLink, String success, String failed) {
        this.checkoutLink = checkoutLink;
        this.success = success;
        this.failed = failed;
    }

    public String getCheckoutLink() {
        return checkoutLink;
    }

    public void setCheckoutLink(String checkoutLink) {
        this.checkoutLink = checkoutLink;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }
}
