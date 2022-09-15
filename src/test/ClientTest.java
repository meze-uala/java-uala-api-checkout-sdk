package test;

import main.client.Client;
import main.config.Config;
import org.junit.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void createAClientWithInvalidEnvironmentShouldCreateStageInstance(){
        Config cfg = new Config("NARNIA");
        Client cli = new Client(cfg);

        assertEquals("stage", cli.getConfig().getEnvironment());
    }

    @Test
    public void createAClientWithoutPropertyVariableValueShouldCreateStageInstance(){

        System.setProperty("ENVIRONMENT", "SINALOA");
        Client cli = new Client();

        assertEquals("stage", cli.getConfig().getEnvironment());
    }

    @Test
    public void createAClientWithPropertyVariableValueProdShouldCreateProdInstance(){

        System.setProperty("ENVIRONMENT", "PRODUCTION");
        Client cli = new Client();

        assertEquals("production", cli.getConfig().getEnvironment());
    }

    @Test
    public void createAClientWithPropertyVariableValueStageShouldCreateStageInstance(){

        System.setProperty("ENVIRONMENT", "StaGe");
        Client cli = new Client();

        assertEquals("stage", cli.getConfig().getEnvironment());
    }

    @Test
    public void createATestWithStageEnvironmentShouldCreateAClientInstance(){
        Client client = new Client("StaGE");
        assertEquals("stage", client.getConfig().getEnvironment());
    }

    @Test
    public void createATestWithProductionEnvironmentShouldCreateAClientInstance(){
        Client client = new Client("PRODUCTION");
        assertEquals("production", client.getConfig().getEnvironment());
    }

    @Test
    public void createATestWithRandomEnvironmentShouldCreateAStageClientInstance(){
        Client client = new Client("BOCA2000");
        assertEquals("stage", client.getConfig().getEnvironment());
    }
}
