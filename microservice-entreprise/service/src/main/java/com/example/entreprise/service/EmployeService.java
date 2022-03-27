package com.example.entreprise.service;

import com.example.entreprise.model.Employee;

import java.util.List;

public interface EmployeService {

    Employee findEmployeeByEmail(String email);

    Employee insertEmployee(Employee newEmploye);

    void addNotifications(int teamNumber, List<String> newNotifications);

    List findEmployeeByTeamNumber(int teamNumber);

    List<String> consumeNotifications(String email);

    List findAll();
}
