package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;


    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("Kodilla", "test_id", new ArrayList<>()));

        URI uri = new URI("https://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards.toArray(new TrelloBoardDto[0]));
        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("Kodilla", fetchedTrelloBoards.get(0).getName());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {

        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        URI uri = new URI("https://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "test task",
                "https://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        // When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        // Then
        assertEquals("1", newCard.getId());
        assertEquals("test task", newCard.getName());
        assertEquals("https://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException{
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");
        URI url = new URI("https://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(url,TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, fetchedTrelloBoards.size());
    }


}