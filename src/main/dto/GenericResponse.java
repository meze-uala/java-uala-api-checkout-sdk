package main.dto;

import com.google.gson.annotations.SerializedName;

public class GenericResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("description")
    private String description;
    @SerializedName("message")
    private String message;
    @SerializedName("Message")
    private String missingTokenMessage;

    public GenericResponse(String code, String description, String message, String missingTokenMessage) {
        this.code = code;
        this.description = description;
        this.message = message;
        this.missingTokenMessage = missingTokenMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMissingTokenMessage() {
        return missingTokenMessage;
    }

    public void setMissingTokenMessage(String missingTokenMessage) {
        this.missingTokenMessage = missingTokenMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
