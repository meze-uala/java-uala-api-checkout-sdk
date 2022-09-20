import client.*;
import config.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}