package main;

import main.client.Client;
import main.dto.AuthRequest;
import main.dto.OrderRequest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Client client = new Client();

        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");

        String respuesta = client.getAuthToken(req);
        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Obteniendo un token...");
        System.out.println("");
        System.out.println("Respuesta de la obtencion de token -->");
        System.out.println("");
        System.out.println(respuesta);
        System.out.println("");

        OrderRequest or = new OrderRequest("12.12", "TE QUIERO DIEGOOOOOOOO!", "javauser",
                "https://www.google.com.ar","https://www.exito.com", "https://www.notif.com.ar",
                "EMPRETIENDA");


        String tokenTemporal = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlMyYW5BTjVYRWFjWU1qTXV2eFlZaiJ9.eyJpc3MiOiJodHRwczovL3VhbGEtYXJnLWFwaS1kZXYudXMuYXV0aDAuY29tLyIsInN1YiI6InFTQlJidE9iRm41R0pKbll2bTJNM3BTbjEzamdIUE1OQGNsaWVudHMiLCJhdWQiOiJodHRwczovL3VhbGEtYXJnLWFwaS1kZXYudXMuYXV0aDAuY29tL2FwaS92Mi8iLCJpYXQiOjE2NjMwOTU5NzcsImV4cCI6MTY2MzE4MjM3NywiYXpwIjoicVNCUmJ0T2JGbjVHSkpuWXZtMk0zcFNuMTNqZ0hQTU4iLCJzY29wZSI6ImFwaV9jaGVja291dDpjcmVhdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.oWKxcjt2pcwETcKz4-pO_JXLChlpb2Mwf213oY3VUWE21faKcg2aoZbD0Ri0OX6tksKH3g-EylV2ZqfFrJ34zUfHUN6fSR1CC7AY7CobpMWSXsoVsPjTQK4BZdIonaMM0lhdkl8S4v-PbFKh-XLlmWsCeSXNG4ju6rJqXOCgTG6hefW9GJYEHBCKZYhEE-FvmQEUdZ6Hh74tmJUAMnx8tpcLeL3QMv0texp2035D3apBUGDVoiX1WRrwvqglbpOG1XbOCg1kty18RvtrJcAVaRJsT3n4b6zH-FvyVVDQ-p9sdR1xwk4u4-8p2oc49A-WeTQBTDARzhmfISgH6T7G9A";

        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Creando una orden...");
        System.out.println("");
        String respuesta2 = client.createOrder(or, tokenTemporal);
        System.out.println("");
        System.out.println("Respuesta de la orden-->");
        System.out.println("");
        System.out.println(respuesta2);
        System.out.println("");


        System.out.println(">>>>>>>>>>>>>>>>>");
        System.out.println("Obteniendo una orden...");
        System.out.println("");
        String orderID = "0fec34b8-b407-4381-8729-efb64c09fca5";
        String respuesta3 = client.getOrder(orderID, tokenTemporal);
        System.out.println("");
        System.out.println("Orden obtenida-->");
        System.out.println("");
        System.out.println(respuesta3);
        System.out.println("");


    }
}
