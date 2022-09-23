import client.*;
import com.google.gson.Gson;
import config.Config;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.GenericResponse;
import dto.OrderRequest;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
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
    public void validRequestShouldPass() {

        Client client = new Client(true);
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
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
    public void validRequestShouldCreateAnOrder(){
       //TODO OrderRequest or = new OrderRequest();
    }
}