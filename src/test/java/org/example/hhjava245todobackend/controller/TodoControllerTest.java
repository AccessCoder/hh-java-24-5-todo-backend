package org.example.hhjava245todobackend.controller;

import org.example.hhjava245todobackend.model.Todo;
import org.example.hhjava245todobackend.repo.TodoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepo repo;

    @Test
    void getAllTodos_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllTodos_shouldReturnListWithOneTodo_whenCalledWithFilledDB() throws Exception {
        Todo todo = new Todo("1", "Test", "DONE");
        repo.save(todo);

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                        [
                                          {
                                            "id": "1",
                                            "description": "Test",
                                            "status": "DONE"
                                          }
                                        ]
                                        """));
    }

    @Test
    void getTodoById_shouldReturnTodo_whenCalledWithValidId() throws Exception {
        Todo todo = new Todo("1", "Test", "DONE");
        repo.save(todo);

        mockMvc.perform(get("/api/todo/"+todo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                        
                                          {
                                            "id": "1",
                                            "description": "Test",
                                            "status": "DONE"
                                          }
                                        
                                        """));
    }

    @Test
    void createTodo_shouldReturnCreatedTodo_whenCalledWithDTO() throws Exception {
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "description": "Test",
                          "status": "OPEN"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                           "description": "Test",
                                           "status": "OPEN" 
                                          }
                                          """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateTodo_shouldReturnUpdatedTodo_whenCalledWithNewData() throws Exception {
        Todo todo = new Todo("1", "Test", "DONE");
        repo.save(todo);

        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "id": "1",
                          "description": "Test",
                          "status": "IN_PROGRESS"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                           "id": "1",
                                           "description": "Test",
                                           "status": "IN_PROGRESS" 
                                          }
                                          """));
    }

    @Test
    void deleteTodo_shouldReturnDeletedTodo_whenCalledWithValidID() throws Exception {
        Todo todo = new Todo("1", "Test", "DONE");
        repo.save(todo);

        mockMvc.perform(delete("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                           "id": "1",
                                           "description": "Test",
                                           "status": "DONE" 
                                          }
                                          """));
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

}