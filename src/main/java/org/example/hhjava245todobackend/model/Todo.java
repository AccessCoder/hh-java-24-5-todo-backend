package org.example.hhjava245todobackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Todos")
public record Todo(String id,
                   String description,
                   String status) {
}
