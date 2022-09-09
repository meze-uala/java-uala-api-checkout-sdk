package main;

import main.client.Client;
import main.config.Config;
import main.dto.AuthRequest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("FAAAH_EL_DIEGOO");

        Client client = new Client(config);

        AuthRequest req = new AuthRequest("new_user_16319064777", "5qqGKGm4EaawnAH0J6xluc6AWdQBvLW3",
                "cVp1iGEB-DE6KtL4Hi7tocdopP2pZxzaEVciACApWH92e8_Hloe8CD5ilM63NppG","client_credentials");

        String respuesta = client.getAuthToken(req);

        System.out.println(respuesta);

    }
}
