package utils;

import client.Client;
import com.google.gson.Gson;
import dto.AuthRequest;
import dto.AuthResponse;

import java.io.IOException;

public class Utils {

    public Utils(){}

    //TODO: Lo mismo para el resto de los metodos
    //FIXME: Agreguemos documentacion de las funciones con javadoc, facilita su uso en el desarrollo (devExperience)
    public String getToken(Client cli) throws IOException {
        //FIXME: Usemos variables de entorno para no tener estos valores hardcodeados y poder manejar desde los secretos los diferentes ambientes
        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        AuthResponse ar = cli.getEncoder().fromJson(cli.getAuthToken(req), AuthResponse.class);
        return ar.getAccessToken();
    }

    public String getTokenFromResponse(String response) throws IOException {
        try {
            Gson encoder = new Gson();
            AuthResponse ar = encoder.fromJson(response, AuthResponse.class);
            return ar.getAccessToken();
        } catch (Exception e){
            //TODO: Lo mismo para el resto de los try{}catch{}
            //FIXME: No salir a production/publicar sin logs.
            return null;
        }
    }
}
