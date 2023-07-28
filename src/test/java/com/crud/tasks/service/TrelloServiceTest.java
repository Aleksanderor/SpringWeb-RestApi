package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrelloServiceTest {

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private TrelloService trelloService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> boards = new ArrayList<>();
        boards.add(new TrelloBoardDto("board1", "id1", new ArrayList<>()));
        boards.add(new TrelloBoardDto("board2", "id2", new ArrayList<>()));
        when(trelloClient.getTrelloBoards()).thenReturn(boards);

        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertEquals(boards.size(), result.size());
        assertEquals(boards, result);
    }

    @Test
    public void testCreateTrelloCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Description", "1", "2");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("123", "Test Card", "url");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("olek@example.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);

        // Then
        Assertions.assertNotNull(result);
        assertEquals(createdTrelloCardDto, result);
        verify(emailService, times(1)).send(any(Mail.class));
    }

    @Test
    public void shouldNotCreateTrelloCardWhenTrelloClientReturnsNull() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Description", "1", "2");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(null);

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);

        // Then
        assertEquals(null, result);
        verify(emailService, never()).send(any(Mail.class));
    }
}
