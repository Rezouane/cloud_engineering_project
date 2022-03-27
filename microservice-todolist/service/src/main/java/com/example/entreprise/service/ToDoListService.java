package com.example.entreprise.service;

import com.example.entreprise.model.ToDoList;

import java.util.List;

public interface ToDoListService {
    boolean isExistingEmployee(String email);

    ToDoList addEmployeeToToDoList(String toDoListId, String employeeEmail);

    ToDoList insertToDoList(ToDoList toDoList);

    ToDoList findToDoListById(String toDoListId);

    List<ToDoList> findAllToDoList();

    ToDoList finishToDoList(String toDoListId);
}
