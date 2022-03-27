package com.example.entreprise.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ToDoList")
public class ToDoList {

    @Id
    private String _id;

    private String title;
    private String description;
    private List<String> employees;
    private boolean isFinished;
    private int teamNumber;

    public ToDoList(String title, String description, List<String> employees, boolean isFinished, int teamNumber) {
        super();
        this.title = title;
        this.description = description;
        this.employees = employees;
        this.isFinished = isFinished;
        this.teamNumber = teamNumber;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void addEmployee(String employeEmail) {
        if (!this.employees.contains(employeEmail) && !this.isFinished) {
            this.employees.add(employeEmail);
        }
    }

    public int getTeamNumber() {
        return this.teamNumber;
    }
}
