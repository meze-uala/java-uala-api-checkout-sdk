package config;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void TestStageConfig(){
        Config c  = new Config("STaGe");
        assertEquals(Environment.STAGE.getEnvironment(), c.getEnvironment());
        assertEquals(ApiBaseUrl.STAGE.getBaseUrl(), c.getApiBaseUrl());
        assertEquals(AuthApiBaseUrl.STAGE.getAuthUrl(), c.getAuthAPIbaseUrl());
    }

    @Test
    public void TestProductionConfig(){
        Config c = new Config("PRODUCTION");
        assertEquals(Environment.PRODUCTION.getEnvironment(), c.getEnvironment());
        assertEquals(ApiBaseUrl.PRODUCTION.getBaseUrl(), c.getApiBaseUrl());
        assertEquals(AuthApiBaseUrl.PRODUCTION.getAuthUrl(), c.getAuthAPIbaseUrl());
    }

    @Test
    public void TestInvalidEnvironmentConfigShouldReturnStageConfig(){
        Config c  = new Config("ELDIEGOTEEE");
        assertEquals(Environment.STAGE.getEnvironment(), c.getEnvironment());
        assertEquals(ApiBaseUrl.STAGE.getBaseUrl(), c.getApiBaseUrl());
        assertEquals(AuthApiBaseUrl.STAGE.getAuthUrl(), c.getAuthAPIbaseUrl());
    }

}