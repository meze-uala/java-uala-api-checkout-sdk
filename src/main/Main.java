package main;

import main.client.Client;
import main.config.Config;
import main.dto.AuthRequest;
import main.dto.OrderRequest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Client client = new Client();

        AuthRequest req = new AuthRequest("new_user_1631906477", "5qqGKGm4EaawnAH0J6xluc6AWdQBvLW3",
                "cVp1iGEB-DE6KtL4Hi7tocdopP2pZxzaEVciACApWH92e8_Hloe8CD5ilM63NppG","client_credentials");

        String respuesta = client.getAuthToken(req);
        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Obteniendo un token...");
        System.out.println("");
        System.out.println("Respuesta de la obtencion de token -->");
        System.out.println("");
        System.out.println(respuesta);
        System.out.println("");

        OrderRequest or = new OrderRequest("12.12", "TE QUIERO DIEGOOOOOOOO!", "new_user_1631906477",
                "https://www.google.com.ar","https://www.exito.com", "https://www.notif.com.ar",
                "EMPRETIENDA");


        String tokenTemporal = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5Ua01mWF82QVhWbnJHQTVLWF8zYyJ9.eyJpc3MiOiJodHRwczovL3VhbGEtYXJnLWFwcC1zdGFnZS51cy5hdXRoMC5jb20vIiwic3ViIjoiNXFxR0tHbTRFYWF3bkFIMEo2eGx1YzZBV2RRQnZMVzNAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vdWFsYS1hcmctYXBwLXN0YWdlLnVzLmF1dGgwLmNvbS9hcGkvdjIvIiwiaWF0IjoxNjYyNzU0NTA3LCJleHAiOjE2NjI4NDA5MDcsImF6cCI6IjVxcUdLR200RWFhd25BSDBKNnhsdWM2QVdkUUJ2TFczIiwic2NvcGUiOiJjcmVhdGU6Y2xpZW50X2dyYW50cyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.W13HiuFD5YB9pgNC4WYvifJb8FA14dV_A6oabdkYWUAkTu8vouVqJ4MACJObWx_KzniM4bLrA7xOdf_5lORF8tFk25CLNMRa68dRfuwEDJHhkVj82t6eeLNnibetkR3yovMQEhGDVliQ10qLJQlXd4ku8JCnSAUQ6Tmoy25l82LksgHb38k_vDPSo34ALB3TmmWI9rsnUnyQP0axEoXq11cCG--t_3jqPPAvzX2WIx5PgrVlRijP7rzNkM3IO_LIoNuuH9wbqDyodi0txa0_NNEFZLE4LOukHfPN_I8GhyiY1Dy2fZ0vCIThslfVnME4NNGDCOTy2BzMyWQdu72nkA";

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Creando una orden...");
        System.out.println("");
        String respuesta2 = client.createOrder(or, tokenTemporal);
        System.out.println("");
        System.out.println("Respuesta de la orden-->");
        System.out.println("");
        System.out.println(respuesta2);
        System.out.println("");
    }
}
