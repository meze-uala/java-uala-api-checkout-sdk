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


        String tokenTemporal = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlMyYW5BTjVYRWFjWU1qTXV2eFlZaiJ9.eyJpc3MiOiJodHRwczovL3VhbGEtYXJnLWFwaS1kZXYudXMuYXV0aDAuY29tLyIsInN1YiI6InFTQlJidE9iRm41R0pKbll2bTJNM3BTbjEzamdIUE1OQGNsaWVudHMiLCJhdWQiOiJodHRwczovL3VhbGEtYXJnLWFwaS1kZXYudXMuYXV0aDAuY29tL2FwaS92Mi8iLCJpYXQiOjE2NjI5OTIxNTMsImV4cCI6MTY2MzA3ODU1MywiYXpwIjoicVNCUmJ0T2JGbjVHSkpuWXZtMk0zcFNuMTNqZ0hQTU4iLCJzY29wZSI6ImFwaV9jaGVja291dDpjcmVhdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.j_mfN64b4I9KO8NwsRKJ1OZNKqlHSoOLMGI4LxjBIDmOb3BpDJyeRXGuXI_Uzn_IMCRAXV0Xci2B9MUXPZrjzr6Fa2zSpyrHQ7SDATZ_4fJmQo5o-2CRzCT3dq2vLYcquGXnvL20FqsMPJB0xjYACKwwuROpq94pi49DMNGnDFBl9fHlYG13zFHRbtzx2xF8JXb9-LxGLF__8I0s3Zl1MEhFvt_86mj_G26huYZuHnOn7KDa9RjsF_5VxHvJr6-w_KzWco9jYTCX--hT4zUgwGEDAYDK_Nh7HtdS3DVfMp0u_h8cDu8aBa1OO1Rdh8h5ie7QMknD_ybxyJjJOJDMYA";

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
