package org.example.hhjava245todobackend.controller;

import org.example.hhjava245todobackend.model.Todo;
import org.example.hhjava245todobackend.model.TodoDTO;
import org.example.hhjava245todobackend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id){
        return service.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody TodoDTO newTodo ){
        return service.createTodo(newTodo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@RequestBody Todo updatedTodo){
        return service.updateTodo(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public Todo deleteTodo(@PathVariable String id){
        return service.deleteTodo(id);
    }
}
