package main.utils;

import main.client.Client;
import main.dto.AuthRequest;
import main.dto.AuthResponse;

import java.io.IOException;

public class Utils {

    public Utils(){}

    public String getToken(Client cli) throws IOException {
        AuthRequest req = new AuthRequest("javauser", "qSBRbtObFn5GJJnYvm2M3pSn13jgHPMN",
                "vVvbYMTmNKggv11naxMfbZ7qbdo6SKS985SwZYE0FSsfewNMKXwpzxemr6DKoQ-8");
        AuthResponse ar = cli.getEncoder().fromJson(cli.getAuthToken(req), AuthResponse.class);
        return ar.getAccessToken();
    }
}
