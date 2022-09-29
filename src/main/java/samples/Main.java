package samples;

import client.Client;
import config.Config;
import dto.AuthRequest;
import dto.OrderRequest;
import utils.Utils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Instance of Client. This constructor read the property with key 'ENVIRONMENT'
        //that allows only 'stage' and 'production'. If none is set, create a Stage instance
        Client client = new Client(true);

        //You can also create an instance of client with others constructors.
        //The other one is passing the environment as String parameter

        System.out.println("Environment of client2: "+ client.getConfig().getEnvironment());

        //And the last one is creating a config and pass it as parameter
        Config sampleConfig = new Config("stage");
        Client client2 = new Client(sampleConfig);
        System.out.println("Environment of client3: "+client2.getConfig().getEnvironment());

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
        OrderRequest or = new OrderRequest("1986.10", "Copa Mundial Original", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");


        //This utils is just for testing purpose, feel free to ignore it
        Utils utils = new Utils();

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Creating checkout order -->");
        String respuesta2 = client.createOrder(or);
        System.out.println("Order response -->");
        System.out.println(respuesta2);
        System.out.println("");


        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Getting an order -->");
        String orderID = "82b41bb1-ae75-40a0-a3c7-ffb35903a484";
        String respuesta3 = client.getOrder(orderID);
        System.out.println("Order retrieved -->");
        System.out.println(respuesta3);
        System.out.println("");

    }


}
