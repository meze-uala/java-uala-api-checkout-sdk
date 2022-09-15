package main.samples;

import main.client.Client;
import main.config.Config;
import main.dto.AuthRequest;
import main.dto.OrderRequest;
import main.utils.Utils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Instance of Client. This constructor read the property with key 'ENVIRONMENT'
        //that allows only 'stage' and 'production'. If none is set, create a Stage instance
        Client client = new Client();

        //You can also create an instance of client with others constructors.
        //The other one is passing the environment as String parameter

        Client client2 = new Client("stage");
        System.out.println("Environment of client2: "+ client.getConfig().getEnvironment());

        //And the last one is creating a config and pass it as parameter
        Config sampleConfig = new Config("stage");
        Client client3 = new Client(sampleConfig);
        System.out.println("Environment of client3: "+client3.getConfig().getEnvironment());

        //Prepare the request
        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");

        //Get the authToken (necessary for create and retrieve orders)
        String respuesta = client.getAuthToken(req);
        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Getting authToken -->");
        System.out.println("AuthToken response -->");
        System.out.println(respuesta);

        //Prepare CreateOrder (also know as 'checkout') request
        OrderRequest or = new OrderRequest("12.12", "TE QUIERO DIEGOOOOOOOO!", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");


        //This utils is just for testing purpose, feel free to ignore it
        Utils utils = new Utils();

        String tempToken = utils.getToken(client);

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Creating checkout order -->");
        String respuesta2 = client.createOrder(or, tempToken);
        System.out.println("Order response -->");
        System.out.println(respuesta2);
        System.out.println("");


        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Getting an order -->");
        String orderID = "0fec34b8-b407-4381-8729-efb64c09fca5";
        String respuesta3 = client.getOrder(orderID, tempToken);
        System.out.println("Order retrieved -->");
        System.out.println(respuesta3);
        System.out.println("");

    }


}
