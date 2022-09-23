package service;

import com.google.gson.Gson;
import config.Config;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.GenericResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService implements IAuthService {

    private Config config;
    private Gson encoder;


    public AuthService(Config config, Gson encoder){
        this.config = config;
        this.encoder = encoder;
    }

    @Override
    public String getAuthToken(AuthRequest request) throws IOException {
        if (!request.validRequest()){
            return invalidAuthRequest();
        }

        URL url = new URL(String.format("%s%s", config.getAuthAPIbaseUrl(), "/token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String gsonRequest = getEncoder().toJson(request);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = gsonRequest.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();

        Reader streamReader = null;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        try {

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (status > 299) {
                GenericResponse gr = getEncoder().fromJson(content.toString(), GenericResponse.class);
                return getEncoder().toJson(gr);
            } else {
                AuthResponse authResponse = getEncoder().fromJson(content.toString(), AuthResponse.class);
                return getEncoder().toJson(authResponse);
            }
        } catch (Exception e){
           throw new IOException("An exception occurred when trying to get authToken");
        }
    }

    public String invalidAuthRequest(){
        GenericResponse response = new GenericResponse("3001", "One or more required fields are empty", null, null);
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Gson getEncoder() {
        return encoder;
    }

    public void setEncoder(Gson encoder) {
        this.encoder = encoder;
    }
}
