package org.example.hhjava245todobackend.service;

import org.example.hhjava245todobackend.model.Todo;
import org.example.hhjava245todobackend.model.TodoDTO;
import org.example.hhjava245todobackend.repo.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo repo;
    private final IdService idService;

    public TodoService(TodoRepo repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }


    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getTodoById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + id));
    }

    public Todo createTodo(TodoDTO newTodo) {
        Todo todo = new Todo(
                idService.generateId(),
                newTodo.description(),
                newTodo.status()
        );
        return repo.save(todo);
    }

    public Todo updateTodo(Todo updatedTodo) {
        if (repo.existsById(updatedTodo.id())){
            return repo.save(updatedTodo);
        }else {
            throw new IllegalArgumentException("No user found with ID: " + updatedTodo.id());
        }
    }


    public Todo deleteTodo(String id) {
        if (repo.existsById(id)){
            Todo deletedTodo = repo.findById(id).orElseThrow();
            repo.deleteById(id);
            return deletedTodo;
        }else {
            throw new IllegalArgumentException("No user found with ID: " + id);
        }
    }
}
