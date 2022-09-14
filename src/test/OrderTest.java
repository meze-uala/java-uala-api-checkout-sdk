package test;

import com.google.gson.Gson;
import main.client.Client;
import main.dto.*;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class OrderTest {

    @Test
    public void invalidRequestShouldReturnErr() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest(null, null, null, null, null, null, null);
        String response = cli.createOrder(or, token);

        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("Invalid request error. One or more mandatory fields are missing.", gr.getMessage());
        assertEquals("1001", gr.getCode());

    }

    @Test
    public void requestMissingAmountShouldReturnErr() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest(null, "description", "javauser", "www.google.com", "www.stackoverflow.com", "www.notifications.com", "TUVIEJA");
        String response = cli.createOrder(or, token);

        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("Invalid request error. One or more mandatory fields are missing.", gr.getMessage());
        assertEquals("1001", gr.getCode());
    }

    @Test
    public void requestMissingUserNameShouldReturnErr() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest("1.12", "description", "", "www.google.com", "www.stackoverflow.com", "www.notifications.com", "TUVIEJA");
        String response = cli.createOrder(or, token);

        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("Invalid request error. One or more mandatory fields are missing.", gr.getMessage());
        assertEquals("1001", gr.getCode());
    }

    @Test
    public void requestMissingCallbackFailShouldReturnErr() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest("1.12", "description", "javauser", "", "www.stackoverflow.com", "www.notifications.com", "TUVIEJA");
        String response = cli.createOrder(or, token);

        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("Invalid request error. One or more mandatory fields are missing.", gr.getMessage());
        assertEquals("1001", gr.getCode());
    }

    @Test
    public void requestMissingCallbackSuccessShouldReturnErr() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest("1.12", "description", "javauser", "https://google.com.ar", "", "www.notifications.com", "TUVIEJA");
        String response = cli.createOrder(or, token);

        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("Invalid request error. One or more mandatory fields are missing.", gr.getMessage());
        assertEquals("1001", gr.getCode());
    }

    @Test
    public void requestMissingDescriptionNotificationUrlAndOriginShouldCreateOrder() throws IOException {

        Gson gson = new Gson();

        Client cli = new Client();
        String token = getToken(cli);
        OrderRequest or = new OrderRequest("1.12", null, "javauser", "https://google.com.ar", "www.falso.com", null, null);
        String response = cli.createOrder(or, token);

        OrderResponse orderResponse = gson.fromJson(response, OrderResponse.class);

        assertEquals("Order", orderResponse.getType());
        assertNotNull(orderResponse.getId());
        assertNotNull(orderResponse.getUuid());
    }

    public String getToken(Client cli) throws IOException {
        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        AuthResponse ar = cli.getEncoder().fromJson(cli.getAuthToken(req), AuthResponse.class);
        return ar.getAccessToken();
    }
}
