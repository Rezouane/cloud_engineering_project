package com.example.entreprise.web;

import com.example.entreprise.model.Employee;
import com.example.entreprise.service.EmployeService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeRessource {

    private BeanFactory beanFactory;

    public EmployeeRessource(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @GetMapping("/employees/{employeeEmail}")
    public ResponseEntity<Employee> findEmployeeByEmail(@PathVariable("employeeEmail") String employeeEmail) {

        Employee employee = this.beanFactory.getBean(EmployeService.class).findEmployeeByEmail(employeeEmail);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employees/{employeeEmail}/notifications")
    public ResponseEntity<List<String>> getEmployeeNotifications(@PathVariable("employeeEmail") String employeeEmail) {

        List<String> notifications = this.beanFactory.getBean(EmployeService.class).consumeNotifications(employeeEmail);

        if (notifications == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(notifications);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAllEmployes() {

        List<Employee> employees = this.beanFactory.getBean(EmployeService.class).findAll();

        return ResponseEntity.ok().body(employees);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee newEmployee) {
        Employee employee = null;
        try {
            employee = this.beanFactory.getBean(EmployeService.class)
                    .insertEmployee(new Employee(newEmployee.getName(), newEmployee.getLastname(),
                            newEmployee.getEmail(), newEmployee.getTeamNumber(), new ArrayList<>()));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(employee);
    }


}