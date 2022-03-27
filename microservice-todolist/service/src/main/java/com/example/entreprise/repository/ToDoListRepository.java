package com.example.entreprise.repository;

import com.example.entreprise.model.ToDoList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ToDoListRepository extends MongoRepository<ToDoList, String> {

    @Query("{_id:'?0'}")
    ToDoList findToDoListById(String id);

    List findAll();

    public long count();
}
