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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
