import client.Client;
import com.google.gson.Gson;
import dto.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    @Test
    public void invalidRequestBodyShouldReturnErr() throws IOException {
        Client client = new Client();
        AuthRequest request = new AuthRequest(null, null, null);
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3001", gr.getCode());
        assertEquals("One or more required fields are empty", gr.getDescription());
    }

    @Test
    public void validRequestShouldPass() throws IOException {
        Client client = new Client();
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        AuthResponse ar = gson.fromJson(result, AuthResponse.class);

        assertEquals("Bearer", ar.getTokenType());
        assertNotNull(ar.getAccessToken());
        assertNotNull(ar.getExpiresIn());
    }

    @Test
    public void requestWitInvalidUserShouldReturnNotFoundErr() throws IOException {
        Client client = new Client();
        AuthRequest request = new AuthRequest("javauserfake", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3003", gr.getCode());
        assertEquals("User account not found", gr.getDescription());

    }

    @Test
    public void requestWithInvalidClientIdShouldReturnErr() throws IOException {
        Client client = new Client();
        AuthRequest request = new AuthRequest("javauser", "-", "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3005", gr.getCode());
        assertEquals("The provided client id does not correspond to any active user", gr.getDescription());

    }

    @Test
    public void requestWithInvalidClientSecretIdShouldReturnErr() throws IOException {
        Client client = new Client();
        AuthRequest request = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN", "diegol");
        String result = client.getAuthToken(request);

        Gson gson = new Gson();
        GenericResponse gr = gson.fromJson(result, GenericResponse.class);

        assertEquals("3006", gr.getCode());
        assertEquals("Error authenticating user. Invalid credentials", gr.getDescription());

    }

}