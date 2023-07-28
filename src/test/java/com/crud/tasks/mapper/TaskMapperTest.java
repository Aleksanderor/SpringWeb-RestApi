package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testMapToTask() {

        // Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "content.");

        // When
        Task task = taskMapper.mapToTask(taskDto);

        // Then
        Assertions.assertEquals(taskDto.getId(), task.getId());
        Assertions.assertEquals(taskDto.getTitle(), task.getTitle());
        Assertions.assertEquals(taskDto.getContent(), task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        // Given
        Task task = new Task(1L, "Test Task", "content");

        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // Then
        Assertions.assertEquals(task.getId(), taskDto.getId());
        Assertions.assertEquals(task.getTitle(), taskDto.getTitle());
        Assertions.assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        // Given
        List<Task> taskList = Arrays.asList(
                new Task(1L, "Task 1", "Content 1"),
                new Task(2L, "Task 2", "Content 2"),
                new Task(3L, "Task 3", "Content 3")
        );

        // When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        // Then
        Assertions.assertEquals(taskList.size(), taskDtoList.size());

        // Using stream to check each element in the list
        Assertions.assertTrue(taskList.stream()
                .allMatch(task -> {
                    TaskDto taskDto = taskDtoList.stream()
                            .filter(dto -> dto.getId().equals(task.getId()))
                            .findFirst()
                            .orElse(null);

                    return taskDto != null &&
                            task.getTitle().equals(taskDto.getTitle()) &&
                            task.getContent().equals(taskDto.getContent());
                }));
    }
}
