package com.example.entreprise.service.impl;

import com.example.entreprise.model.Employee;
import com.example.entreprise.repository.EmployeeRepository;
import com.example.entreprise.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeByEmail(String email) {
        synchronized (employeeRepository) {
            return employeeRepository.findEmployeeByEmail(email);
        }
    }

    @Override
    public Employee insertEmployee(Employee newEmploye) {
        synchronized (employeeRepository) {
            return employeeRepository.save(newEmploye);
        }
    }

    @Override
    public void addNotifications(int teamNumber, List<String> newNotifications) {
        synchronized (employeeRepository) {
            List<Employee> teamMembers = this.findEmployeeByTeamNumber(teamNumber);
            teamMembers.forEach(employe -> {
                employe.addNotifications(newNotifications);
                this.employeeRepository.save(employe);
            });
        }
    }

    @Override
    public List findEmployeeByTeamNumber(int teamNumber) {
        synchronized (employeeRepository) {
            return this.employeeRepository.findEmployeeByTeamNumber(teamNumber);
        }
    }

    @Override
    public List<String> consumeNotifications(String email) {
        synchronized (employeeRepository) {
            Employee employe = this.findEmployeeByEmail(email);
            if (employe == null) {
                return null;
            }
            List<String> notifications = new ArrayList<>(employe.getNotifications());
            employe.dropNotifications();
            this.employeeRepository.save(employe);
            return notifications;
        }
    }

    @Override
    public List findAll() {
        synchronized (employeeRepository) {
            return employeeRepository.findAll();
        }
    }
}
