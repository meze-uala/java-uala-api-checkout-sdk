import client.Client;
import com.google.gson.Gson;
import config.Config;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.GenericResponse;
import org.junit.Test;
import service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    public void requestWitInvalidUserShouldReturnNotFoundErr() {
        Client client = new Client(true);
        AuthRequest request = new AuthRequest("javauserfake", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3003", gr.getCode());
        assertEquals("User account not found", gr.getDescription());

    }

    @Test
    public void requestWithInvalidClientIdShouldReturnErr() {
        Client client = new Client(true);
        AuthRequest request = new AuthRequest("javauser", "-", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3005", gr.getCode());
        assertEquals("The provided client id does not correspond to any active user", gr.getDescription());

    }

    @Test
    public void requestWithInvalidClientSecretIdShouldReturnErr() {
        Client client = new Client(true);
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "diegol");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3006", gr.getCode());
        assertEquals("Error authenticating user. Invalid credentials", gr.getDescription());

    }

    @Test
    public void invalidRequestShouldReturnErr() {
        Client client = new Client(true);
        AuthRequest request = new AuthRequest(null, "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "diegol");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3001", gr.getCode());
        assertEquals("One or more required fields are empty", gr.getDescription());

    }

    @Test
    public void requestWithValidRequestShouldReturnAToken() {
        Client client = new Client(true);
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        AuthResponse ar = gson.fromJson(result, AuthResponse.class);

        assertNotNull( ar.getAccessToken());
        assertEquals("Bearer", ar.getTokenType());

    }

    @Test
    public void serviceWithEncoderShouldReturnIt(){
        Client cli = new Client(true);
        assertNotNull(cli.getAuthService().getEncoder());
    }

    @Test
    public void serviceWithNullEncoderShouldReturnNullEncoder(){
        Client cli = new Client(true);
        cli.getAuthService().setEncoder(null);
        assertNull(cli.getAuthService().getEncoder());
    }

    @Test
    public void invalidConfigShouldBeRetreivedToo(){
        Config cfg = new Config("stage");
        AuthService as = new AuthService(cfg, null);
        assertNotNull(as.getConfig());

        as.setConfig(null);
        assertNull(as.getConfig());
    }
}
