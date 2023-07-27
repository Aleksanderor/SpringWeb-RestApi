package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @BeforeEach
    void setUp() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    void mapToCardTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Important Task", "Do this first!", "Top", "CC1-1");

        // When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // Then
        assertEquals("Important Task", trelloCard.getName());
        assertEquals("Do this first!", trelloCard.getDescription());
        assertEquals("Top", trelloCard.getPos());
        assertEquals("CC1-1", trelloCard.getListId());
    }

    @Test
    void mapToCardDtoTest() {
        // Given
        TrelloCard trelloCard = new TrelloCard("Urgent", "ASAP", "Bottom", "CC1-2");

        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals("Urgent", trelloCardDto.getName());
        assertEquals("ASAP", trelloCardDto.getDescription());
        assertEquals("Bottom", trelloCardDto.getPos());
        assertEquals("CC1-2", trelloCardDto.getListId());
    }

    @Test
    void mapToTrelloBoardsDtoTest() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("Later", "1", new ArrayList<>()));
        trelloBoardList.add(new TrelloBoard("Now", "2", Arrays.asList(new TrelloList("1", "first", true), new TrelloList("2", "second", false))));
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        int listSize = trelloBoardList.size();
        //Then
        assertEquals(2, listSize);
    }

    @Test
    void mapToTrelloListTest() {
        // Given
        List<TrelloListDto> trelloListDtoList = Arrays.asList(
                new TrelloListDto("1", "To Do", false),
                new TrelloListDto("2", "In Progress", false),
                new TrelloListDto("3", "Done", true)
        );

        // When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtoList);

        // Then
        assertEquals(3, trelloLists.size());
        assertEquals("1", trelloLists.get(0).getId());
        assertEquals("To Do", trelloLists.get(0).getName());
        assertFalse(trelloLists.get(0).isClosed());

        assertEquals("2", trelloLists.get(1).getId());
        assertEquals("In Progress", trelloLists.get(1).getName());
        assertFalse(trelloLists.get(1).isClosed());

        assertEquals("3", trelloLists.get(2).getId());
        assertEquals("Done", trelloLists.get(2).getName());
        assertTrue(trelloLists.get(2).isClosed());
    }

    @Test
    void mapToTrelloListDtoTest() {
        // Given
        List<TrelloList> trelloLists = Arrays.asList(
                new TrelloList("1", "New List", false),
                new TrelloList("2", "Another List", true)
        );

        // When
        List<TrelloListDto> trelloListDtoList = trelloMapper.mapToListDto(trelloLists);

        // Then
        assertEquals(2, trelloListDtoList.size());
        assertEquals("1", trelloListDtoList.get(0).getId());
        assertEquals("New List", trelloListDtoList.get(0).getName());
        assertFalse(trelloListDtoList.get(0).isClosed());

        assertEquals("2", trelloListDtoList.get(1).getId());
        assertEquals("Another List", trelloListDtoList.get(1).getName());
        assertTrue(trelloListDtoList.get(1).isClosed());
    }

    @Test
    void mapToTrelloBoardsTest() {
        // Given
        List<TrelloBoardDto> trelloBoardDtoList = Arrays.asList(
                new TrelloBoardDto("Board 1", "1", Collections.emptyList()),
                new TrelloBoardDto("Board 2", "2",
                        Arrays.asList(
                                new TrelloListDto("1", "List 1", true),
                                new TrelloListDto("2", "List 2", false)
                        )
                )
        );

        // When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        // Then
        assertEquals(trelloBoardDtoList.size(), trelloBoardList.size());
        assertEquals(trelloBoardDtoList.get(0).getId(), trelloBoardList.get(0).getId());
        assertEquals(trelloBoardDtoList.get(0).getName(), trelloBoardList.get(0).getName());
        assertEquals(trelloBoardDtoList.get(0).getLists().size(), trelloBoardList.get(0).getLists().size());

        assertEquals(trelloBoardDtoList.get(1).getId(), trelloBoardList.get(1).getId());
        assertEquals(trelloBoardDtoList.get(1).getName(), trelloBoardList.get(1).getName());
        assertEquals(trelloBoardDtoList.get(1).getLists().size(), trelloBoardList.get(1).getLists().size());
    }


}