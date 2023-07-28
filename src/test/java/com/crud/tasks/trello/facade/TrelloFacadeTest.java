package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrelloFacadeTest {

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloValidator trelloValidator;

    @InjectMocks
    private TrelloFacade trelloFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFetchEmptyList() {
        // Given
        List<TrelloBoardDto> emptyBoards = Collections.emptyList();
        when(trelloService.fetchTrelloBoards()).thenReturn(emptyBoards);

        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();

        // Then
        assertEquals(0, result.size());
    }
}