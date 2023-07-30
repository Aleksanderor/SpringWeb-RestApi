package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    DbService service;

    @Test
    void shouldGetTask() throws Exception {

        // Given
        Task task = new Task(1L, "Test Title", "Test Content");
        TaskDto taskDto = new TaskDto(1L, "Test Dto", "Dto Content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(task);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test Dto")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Dto Content")));
    }

    @Test
    void shouldGetTasks() throws Exception{

        //Given
        List<Task> tasks = new ArrayList<>();
        when(service.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }


    @Test
    void shouldDeleteTask()throws Exception{

        //Given
        Task task = new Task(1L, "Title", "Content");
        when(service.getTaskById(task.getId())).thenReturn(task);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Dto", "Test Content Dto");
        Task task = new Task(1L, "Test Title", "Test Content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Test Dto")))
                .andExpect(jsonPath("$.content", Matchers.is("Test Content Dto")));
    }

    @Test
    void shouldCreateTask() throws Exception{

        //Given
        TaskDto taskDto = new TaskDto(1L, "Dto", "Content Dto");
        Task task = new Task(1L, "Title", "Content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

}