package main.client;

import main.config.Config;
import main.dto.AuthRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    private Config config;

    public Client(Config config) {
        this.config = config;
    }

    public Client(){
        //TODO por ahora es un STAGE mock pero este constructor servira para indicarle por env variable el environment
        Config defaultConfig = new Config("STAGE");
        this.config = defaultConfig;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    // Auth
    public String getAuthToken(AuthRequest request) throws IOException {

        //TODO evaluar si vale la pena poner un validator antes, evitando asi un request en vano
        /*
        if (!request.validRequest()){
            //Usar el AuthResponse to json
            return "Error on validateRequest";
        }
        */

        URL url = new URL(String.format("%s%s", config.getAuthAPIbaseUrl(), "/token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        //TODO ver si no hay una forma nativa mas linda de hacer este request... (sin utilizar jackson o gson)
        String authRequestStr = "{" + '"'+"user_name"+'"' +":"+request.getUsername()+ "," +
                '"'+ "client_id"+'"' +":"+'"'+request.getClientID()+'"'+","+
                '"'+ "client_secret_id" +'"'+":"+'"'+request.getClientSecretID()+'"'+ ","+
                '"'+"grant_type" + '"'+":"+'"'+request.getGrantType() +'"'+
                "}";

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = authRequestStr.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e){
            return "Error al intentar enviar el req";
        }


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();

        } catch (Exception e) {
            //TODO ver el response en caso de fail. Actualmente ante una falta de user por ej, retorna esto y no lo mismo q por otro Cliente
            return "{"+'"'+"message"+'"'+":"+'"'+"an error occurred when trying to auth"+'"'+"}";
        }


    }

    //CreateOrder

    public String createOrder(){
    return null;
    }

    //GetOrder
    public String getOrder(){
    return null;
    }
}
