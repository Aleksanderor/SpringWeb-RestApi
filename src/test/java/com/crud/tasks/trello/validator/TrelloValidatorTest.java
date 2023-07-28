package com.crud.tasks.trello.validator;
import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloValidatorTest {

    private TrelloValidator trelloValidator;

    @BeforeEach
    public void setUp() {
        trelloValidator = new TrelloValidator();
    }
    @Test
    public void testValidateTrelloBoards_Filtering() {
        // Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("Board 1", "1", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("Test Board", "2", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("Board 3", "3", new ArrayList<>()));

        // When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        // Then
        Assertions.assertEquals(3, filteredBoards.size());
        Assertions.assertTrue(filteredBoards.stream().noneMatch(board -> board.getName().equalsIgnoreCase("Test Board")));
    }
}