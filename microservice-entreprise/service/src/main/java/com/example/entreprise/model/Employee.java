package com.example.entreprise.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("employees")
public class Employee {

    @Id
    private String _id;

    private String name;
    private String lastname;
    private int teamNumber;
    private List<String> notifications;

    @Indexed(unique = true)
    private String email;

    public Employee(String name, String lastname, String email, int teamNumber, List<String> notifications) {
        super();
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.teamNumber = teamNumber;
        this.notifications = notifications;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getTeamNumber() {
        return this.teamNumber;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void dropNotifications() {
        this.notifications.clear();
    }

    public void addNotifications(List<String> notification) {
        this.notifications.addAll(notification);
    }
}
