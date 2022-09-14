package main.samples;

import main.client.Client;
import main.dto.AuthRequest;
import main.dto.OrderRequest;
import main.utils.Utils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Instance of Client :)
        Client client = new Client();

        //Prepare the request
        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");

        //Get the authToken (necessary for create and retrieve orders)
        String respuesta = client.getAuthToken(req);
        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Getting authToken");
        System.out.println("Respuesta de la obtencion de token -->");
        System.out.println(respuesta);

        //Prepare CreateOrder (also know as 'checkout') request
        OrderRequest or = new OrderRequest("12.12", "TE QUIERO DIEGOOOOOOOO!", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");


        //This utils is just for testing purpose, feel free to ignore it
        Utils utils = new Utils();

        String tempToken = utils.getToken(client);

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Creando una orden...");
        String respuesta2 = client.createOrder(or, tempToken);
        System.out.println("Respuesta de la orden-->");
        System.out.println(respuesta2);
        System.out.println("");


        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Obteniendo una orden...");
        String orderID = "0fec34b8-b407-4381-8729-efb64c09fca5";
        String respuesta3 = client.getOrder(orderID, tempToken);
        System.out.println("Orden obtenida-->");
        System.out.println(respuesta3);
        System.out.println("");

    }


}
