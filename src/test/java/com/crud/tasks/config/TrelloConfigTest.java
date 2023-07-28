package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloConfigTest {

    @Test
    void testGetValues(){

        //Given
        TrelloConfig trelloConfig = new TrelloConfig();

        //When
        String trelloUser = trelloConfig.getTrelloUser();
        String trelloToken = trelloConfig.getTrelloToken();
        String trelloAppKey = trelloConfig.getTrelloAppKey();
        String trelloApiEndpoint = trelloConfig.getTrelloApiEndpoint();

        //Then
        assertEquals(trelloUser, trelloConfig.getTrelloUser());
        assertEquals(trelloApiEndpoint, trelloConfig.getTrelloApiEndpoint());
        assertEquals(trelloAppKey, trelloConfig.getTrelloAppKey());
        assertEquals(trelloToken, trelloConfig.getTrelloToken());
    }
}
