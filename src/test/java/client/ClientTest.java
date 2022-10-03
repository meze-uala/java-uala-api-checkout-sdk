package client;

import com.google.gson.Gson;
import config.Config;
import dto.*;
import org.junit.Test;
import service.AuthService;
import service.NotificationService;
import service.OrderService;

import java.io.IOException;
import java.util.ArrayList;

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
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService,mockedNotificationService);
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
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Gson gson = new Gson();

        AuthResponse mar = new AuthResponse("abcdefghijklmnopqrstuvwxyz",
                678, "Bearer");

        Client client = new Client(true, mockedAuthService, mockedOrderService,mockedNotificationService);
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
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService,mockedNotificationService);
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
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService,mockedNotificationService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "javauser",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        OrderResponse response = new OrderResponse("1","Order", "abcdefg.123123", "10", "032","10","APPROVED", "1",null);

        when(mockedOrderService.createOrder(request, "invalidToken")).thenReturn(client.getEncoder().toJson(response));

        client.setToken("invalidToken");

        OrderResponse orderResponse  = client.getEncoder().fromJson(client.createOrder(request), OrderResponse.class);

        assertNotNull(orderResponse.getId());
        assertNotNull(orderResponse.getUuid());
    }

    @Test
    public void invalidRequestShouldNotCreateAnOrder() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        GenericResponse gr = new GenericResponse("mockedCode","mockedDesc","mockedMEssage","mockedMissintTokenMsge");

        when(mockedOrderService.createOrder(request, "invalidToken")).thenReturn(client.getEncoder().toJson(gr));

        client.setToken("invalidToken");
        GenericResponse genericResponse = client.getEncoder().fromJson(client.createOrder(request), GenericResponse.class);

        assertNotNull(genericResponse.getCode());
        assertNotNull(genericResponse.getDescription());
    }

    @Test
    public void exceptionCouldReturnGenericResponseErr() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        OrderRequest request = new OrderRequest("1986.10", "Copa Mundial Original", "",
                "https://www.fracaso.com.ar","https://www.exito.com", "https://www.notify.me.com.ar",
                "ECOMMERCE");

        when(mockedOrderService.createOrder(request, "invalidToken")).thenThrow(new IOException("mockedException"));

        client.setToken("invalidToken");

        GenericResponse genericResponse = client.getEncoder().fromJson(client.createOrder(request), GenericResponse.class);

        assertNull(genericResponse.getMessage());
        assertEquals("999", genericResponse.getCode());
        assertEquals("an error occurred when trying to create order", genericResponse.getDescription());
    }



    @Test
    public void validIDReturnsOrder() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        OrderResponse oresp = new OrderResponse("1", "Order", "1","1","032","1","FALSO", "1", null);

        when(mockedOrderService.getOrder("1", "tokenFalso")).thenReturn(client.getEncoder().toJson(oresp));

        client.setToken("tokenFalso");

        OrderResponse orderResponse = client.getEncoder().fromJson(client.getOrder("1"), OrderResponse.class);

        assertEquals("1", orderResponse.getId());
        assertEquals("FALSO", orderResponse.getStatus());
    }

    @Test
    public void invalidIdReturnGenericResponse() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        GenericResponse gr = new GenericResponse("1006", "no record found","","");

        when(mockedOrderService.getOrder("abc", "tokenFalso")).thenReturn(client.getEncoder().toJson(gr));

        client.setToken("tokenFalso");

        GenericResponse genericResponse = client.getEncoder().fromJson(client.getOrder("abc"), GenericResponse.class);

        assertEquals("1006", genericResponse.getCode());
        assertEquals("no record found", genericResponse.getDescription());
    }

    @Test
    public void exceptionCouldReturnGenericResponseErrOnGetOrder() throws IOException {

        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        when(mockedOrderService.getOrder("1", "tokenFalso")).thenThrow(new IOException("mockedException"));

        client.setToken("tokenFalso");

        GenericResponse genericResponse = client.getEncoder().fromJson(client.getOrder("1"), GenericResponse.class);

        assertEquals("999", genericResponse.getCode());
        assertEquals("an error occurred when trying to get order", genericResponse.getDescription());
    }

    @Test
    public void exceptionCouldReturnGenericResponseErrOnGetFailedNotifications() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        when(mockedNotificationService.getFailedNotifications("token")).thenThrow(new IOException("mockedException"));
        client.setToken("token");

        GenericResponse genericResponse = client.getEncoder().fromJson(client.getFailedNotifications(), GenericResponse.class);

        assertEquals("999", genericResponse.getCode());
        assertEquals("an error occurred when trying to get order", genericResponse.getDescription());
    }

    @Test
    public void getFailedNotificationsReturnsNoRows() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        FailedNotificationResponse failedNotificationResponse = new FailedNotificationResponse(new ArrayList<Notification>());

        when(mockedNotificationService.getFailedNotifications("token")).thenReturn(client.getEncoder().toJson(failedNotificationResponse));

        client.setToken("token");

        FailedNotificationResponse fnr = client.getEncoder().fromJson(client.getFailedNotifications(), FailedNotificationResponse.class);

        assertNotNull(fnr);
        assertEquals(0, fnr.getNotifications().size());
    }

    @Test
    public void getFailedNotificationsReturnsNull() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        when(mockedNotificationService.getFailedNotifications("token")).thenReturn(client.getEncoder().toJson(null));

        client.setToken("token");

        FailedNotificationResponse fnr = client.getEncoder().fromJson(client.getFailedNotifications(), FailedNotificationResponse.class);

        assertNull(fnr);
    }

    @Test
    public void getFailedNotificationsReturnsResults() throws IOException {
        AuthService mockedAuthService = mock(AuthService.class);
        OrderService mockedOrderService = mock(OrderService.class);
        NotificationService mockedNotificationService = mock(NotificationService.class);

        Notification notification = new Notification("uuid", "acountId", 1,0,10.0, "hoy");

        Client client = new Client(true, mockedAuthService, mockedOrderService, mockedNotificationService);

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        FailedNotificationResponse failedNotificationResponse = new FailedNotificationResponse(notifications);

        when(mockedNotificationService.getFailedNotifications("token")).thenReturn(client.getEncoder().toJson(failedNotificationResponse));

        client.setToken("token");

        FailedNotificationResponse fnr = client.getEncoder().fromJson(client.getFailedNotifications(), FailedNotificationResponse.class);

        assertNotNull(fnr);
        assertEquals(1, fnr.getNotifications().size());
        assertEquals(10.0, fnr.getNotifications().get(0).getAmount());
    }

    @Test
    public void aClientCouldBeCreatedWithDiffServices(){
        Client cli = new Client(true,null, null, null);
        assertEquals(null, cli.getAuthService());
        assertEquals(null, cli.getOrderService());
        assertEquals(null, cli.getNotificationService());

        NotificationService ns = new NotificationService(null, null);
        AuthService as = new AuthService(null, null);
        OrderService os = new OrderService(null, null);

        cli.setAuthService(as);
        cli.setOrderService(os);
        cli.setNotificationService(ns);

        assertNotNull(cli.getAuthService());
        assertNotNull(cli.getOrderService());
        assertNotNull(cli.getNotificationService());
    }

    @Test
    public void canCreateADefaultProductiveClient(){
        Client cli = new Client(false);
        assertEquals("production", cli.getConfig().getEnvironment());
    }

    @Test
    public void canCreateAProductiveClientWithoutServices(){
        Client cli = new Client(false, null, null, null);
        assertEquals("production", cli.getConfig().getEnvironment());
    }
}