import client.*;
import com.google.gson.Gson;
import config.Config;
import dto.*;
import org.junit.Test;
import service.AuthService;
import service.OrderService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {

    @Test
    public void createATestWithStageEnvironmentShouldCreateAClientInstance(){
        Client client = new Client(true);
        assertEquals("stage", client.getConfig().getEnvironment());
    }

    @Test
    public void creatingAClientWithoutArgumentsShouldReturnProductionClientInstance(){
        Client client = new Client();
        assertEquals("production", client.getConfig().getEnvironment());
    }

    @Test
    public void createingAClientWithConfigConstructorShouldWork(){
        Config config = new Config("STAGE");
        Client cli = new Client(config);
        assertEquals("stage", cli.getConfig().getEnvironment());
    }

    @Test
    public void createingAClientForProductionEnvironment(){
        Client cli = new Client(false);
        assertEquals("production", cli.getConfig().getEnvironment());
    }

    @Test
    public void setAndRetrieveAConfig(){
       Client cli = new Client(true);
       Config cfg = new Config("STAGE");

       cli.setConfig(null);
       assertNull(cli.getConfig());

       cli.setConfig(cfg);
       assertNotNull(cli.getConfig());
    }

    @Test
    public void setAndRetrieveAuthAndOrderServices(){
        Client cli = new Client(true);

        assertNotNull(cli.getAuthService());
        assertNotNull(cli.getOrderService());

        cli.setAuthService(null);
        cli.setOrderService(null);

        assertNull(cli.getAuthService());
        assertNull(cli.getOrderService());
    }

    //AuthTests
    @Test
    public void invalidRequestBodyShouldReturnErr() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);
        AuthRequest request = new AuthRequest(null, null, null);

        GenericResponse response = new GenericResponse("3001", "One or more required fields are empty", null, null);
        Gson gson = new Gson();
        String mockedGenericResponse = gson.toJson(response);

        when(mockedAuthService.getAuthToken(request)).thenReturn(mockedGenericResponse);

        String result = client.getAuthToken(request);

        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3001", gr.getCode());
        assertEquals("One or more required fields are empty", gr.getDescription());
    }


    @Test
    public void validRequestShouldPass() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Gson gson = new Gson();

        AuthResponse mar = new AuthResponse("abcdefghijklmnopqrstuvwxyz",
                678, "Bearer");

        Client client = new Client(true, mockedAuthService, mockedOrderService);
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");

        when(mockedAuthService.getAuthToken(request)).thenReturn(gson.toJson(mar));


        String result = client.getAuthToken(request);


        AuthResponse ar = gson.fromJson(result, AuthResponse.class);

        assertEquals("Bearer", ar.getTokenType());
        assertNotNull(ar.getAccessToken());
        assertNotNull(ar.getExpiresIn());
    }

    @Test
    public void exceptionShouldBeReturned() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");

        when(mockedAuthService.getAuthToken(request)).thenThrow(new IOException("Exception papa"));

        String result = client.getAuthToken(request);

        Gson gson = new Gson();

        GenericResponse gr2 = gson.fromJson(result, GenericResponse.class);

        assertEquals("999", gr2.getCode());
        assertEquals("an error occurred when trying to get authToken", gr2.getDescription());
    }

    //OrderTests

    @Test
    public void validRequestShouldCreateAnOrder() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        OrderResponse response = new OrderResponse("1","Order", "abcdefg.123123", "10", "032","10","APPROVED", "1",null);

        when(mockedOrderService.createOrder(request, "invalidToken")).thenReturn(client.getEncoder().toJson(response));

        OrderResponse orderResponse  = client.getEncoder().fromJson(client.createOrder(request, "invalidToken"), OrderResponse.class);

        assertNotNull(orderResponse.getId());
        assertNotNull(orderResponse.getUuid());
    }

    @Test
    public void invalidRequestShouldNotCreateAnOrder() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        GenericResponse gr = new GenericResponse("mockedCode","mockedDesc","mockedMEssage","mockedMissintTokenMsge");

        when(mockedOrderService.createOrder(request, "invalidToken")).thenReturn(client.getEncoder().toJson(gr));

        GenericResponse genericResponse = client.getEncoder().fromJson(client.createOrder(request, "invalidToken"), GenericResponse.class);

        assertNotNull(genericResponse.getCode());
        assertNotNull(genericResponse.getDescription());
    }

    @Test
    public void exceptionCouldReturnGenericResponseErr() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        when(mockedOrderService.createOrder(request, "invalidToken")).thenThrow(new IOException("mockedException"));

        GenericResponse genericResponse = client.getEncoder().fromJson(client.createOrder(request, "invalidToken"), GenericResponse.class);

        assertNull(genericResponse.getMessage());
        assertEquals("999", genericResponse.getCode());
        assertEquals("an error occurred when trying to create order", genericResponse.getDescription());
    }



    @Test
    public void validIDReturnsOrder() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        OrderResponse oresp = new OrderResponse("1", "Order", "1","1","032","1","FALSO", "1", null);

        when(mockedOrderService.getOrder("1", "tokenFalso")).thenReturn(client.getEncoder().toJson(oresp));

        OrderResponse orderResponse = client.getEncoder().fromJson(client.getOrder("1", "tokenFalso"), OrderResponse.class);

        assertEquals("1", orderResponse.getId());
        assertEquals("FALSO", orderResponse.getStatus());
    }

    @Test
    public void invalidIdReturnGenericResponse() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        GenericResponse gr = new GenericResponse("1006", "no record found","","");

        when(mockedOrderService.getOrder("abc", "tokenFalso")).thenReturn(client.getEncoder().toJson(gr));

        GenericResponse genericResponse = client.getEncoder().fromJson(client.getOrder("abc", "tokenFalso"), GenericResponse.class);

        assertEquals("1006", genericResponse.getCode());
        assertEquals("no record found", genericResponse.getDescription());
    }

    @Test
    public void exceptionCouldReturnGenericResponseErrOnGetOrder() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService);

        when(mockedOrderService.getOrder("1", "tokenFalso")).thenThrow(new IOException("mockedException"));

        GenericResponse genericResponse = client.getEncoder().fromJson(client.getOrder("1", "tokenFalso"), GenericResponse.class);

        assertEquals("999", genericResponse.getCode());
        assertEquals("an error occurred when trying to get order", genericResponse.getDescription());
    }
}