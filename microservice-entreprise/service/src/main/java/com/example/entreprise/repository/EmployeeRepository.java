package com.example.entreprise.repository;

import com.example.entreprise.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{email:'?0'}")
    Employee findEmployeeByEmail(String email);

    @Query("{teamNumber:?0}")
    List findEmployeeByTeamNumber(int teamNumber);

    List findAll();

    long count();
}
