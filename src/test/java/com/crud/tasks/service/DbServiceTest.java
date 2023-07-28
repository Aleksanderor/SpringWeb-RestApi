package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class DbServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DbService dbService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        // Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Content 1"));
        tasks.add(new Task(2L, "Task 2", "Content 2"));
        tasks.add(new Task(3L, "Task 3", "Content 3"));
        when(taskRepository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        Assertions.assertEquals(tasks.size(), result.size());
        Assertions.assertEquals(tasks, result);
    }

    @Test
    public void testGetTaskById() {
        // Given
        Long taskId = 1L;
        Task task = new Task(taskId, "Test Task", "This is a test task");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTaskById(taskId);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(task, result);
    }

    @Test
    public void testGetTaskByIdNotFound() {

        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When and Then
        Assertions.assertNull(dbService.getTaskById(taskId));
    }

    @Test
    public void testSaveTask() {
        // Given
        Task task = new Task(1L, "Test Task", "This is a test task");
        when(taskRepository.save(task)).thenReturn(task);

        // When
        Task result = dbService.saveTask(task);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(task, result);
    }

    @Test
    public void testGetExistingTask() throws TaskNotFoundException {
        // Given
        Long taskId = 1L;
        Task task = new Task(taskId, "Test Task", "This is a test task");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTask(taskId);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(task, result);
    }

    @Test
    public void testGetTaskNotFound() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When and Then
        Assertions.assertThrows(TaskNotFoundException.class, () -> dbService.getTask(taskId));
    }

    @Test
    public void testDeleteTask() {
        // Given
        Long taskId = 1L;

        // When
        dbService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}