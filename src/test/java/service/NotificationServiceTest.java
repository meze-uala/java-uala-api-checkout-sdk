package service;

import client.Client;
import com.google.gson.Gson;
import config.Config;
import dto.AuthRequest;
import dto.FailedNotificationResponse;
import dto.GenericResponse;
import org.junit.Test;
import utils.Utils;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationServiceTest {

    @Test
    public void validDataShouldReturnNoResults() throws IOException {
        Gson gson = new Gson();

        Utils utils = new Utils();

        Client cli = new Client(true);
        String token = utils.getToken(cli);
        cli.setToken(token);
        String response = cli.getFailedNotifications();
        FailedNotificationResponse fnr = gson.fromJson(response, FailedNotificationResponse.class);

        assertEquals(0, fnr.getNotifications().size());
    }

    @Test
    public void validDataShouldReturnResults() throws IOException {
        Gson gson = new Gson();

        Client cli = new Client(true);

        AuthRequest request = new AuthRequest("new_user_1631906477", "5qqGKGm4EaawnAH0J6xluc6AWdQBvLW3",
                "cVp1iGEB-DE6KtL4Hi7tocdopP2pZxzaEVciACApWH92e8_Hloe8CD5ilM63NppG");
        cli.getAuthToken(request);
        String response = cli.getFailedNotifications();
        FailedNotificationResponse fnr = gson.fromJson(response, FailedNotificationResponse.class);

        assertNotEquals(0, fnr.getNotifications().size());
    }

    @Test
    public void getNoficationsWithoutTokenReturnsGenericErr() throws IOException {
        Gson gson = new Gson();

        Client cli = new Client(true);

        String response = cli.getFailedNotifications();
        GenericResponse gr = gson.fromJson(response, GenericResponse.class);

        assertEquals("User is not authorized to access this resource with an explicit deny", gr.getMissingTokenMessage());
    }

    @Test
    public void serviceWithDifferentConfigAndEncoderNullCouldBeCreated(){
        NotificationService ns = new NotificationService(null, null);

        assertEquals(null, ns.getEncoder());
        assertEquals(null, ns.getConfig());


        Config cfg = new Config("STAGE");
        ns.setConfig(cfg);
        ns.setEncoder(new Gson());

        assertNotNull(ns.getEncoder());
        assertNotNull(ns.getConfig());
    }

}
