package com.example.entreprise.service.impl;

import com.example.entreprise.model.ToDoList;
import com.example.entreprise.repository.ToDoListRepository;
import com.example.entreprise.service.ToDoListService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ToDoListImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    private final String urlApi = "/employees/";

    @Autowired
    DiscoveryClient discoveryClient;

    @Override
    public boolean isExistingEmployee(String email) {
        List<ServiceInstance> instances = discoveryClient.getInstances("micro-service-entreprise");
        ServiceInstance test = instances.get(0);
        String hostname = test.getHost();

        RestTemplate restTemplate = new RestTemplate();
        final String urlRequest = "http://" + hostname + ":" + test.getPort() + urlApi + email;
        ResponseEntity<String> response = restTemplate.getForEntity(urlRequest, String.class);
        JSONObject employe = null;
        try {
            employe = (JSONObject) new JSONParser().parse(response.getBody());
        } catch (ParseException e) {
            return false;
        }

        return employe.get("email").toString().equals(email);
    }

    @Override
    public ToDoList addEmployeeToToDoList(String toDoListId, String employeeEmail) {
        if (isExistingEmployee(employeeEmail)) {
            ToDoList toDoList = this.toDoListRepository.findToDoListById(toDoListId);
            if (toDoList.isFinished() || toDoList.getEmployees().contains(employeeEmail)) {
                return null;
            }
            toDoList.addEmployee(employeeEmail);
            return this.toDoListRepository.save(toDoList);
        }
        return null;
    }

    @Override
    public ToDoList insertToDoList(ToDoList toDoList) {
        return this.toDoListRepository.save(toDoList);
    }

    @Override
    public ToDoList findToDoListById(String toDoListId) {
        return this.toDoListRepository.findToDoListById(toDoListId);
    }

    @Override
    public List<ToDoList> findAllToDoList() {
        return this.toDoListRepository.findAll();
    }

    @Override
    public ToDoList finishToDoList(String toDoListId) {
        ToDoList toDoList = this.toDoListRepository.findToDoListById(toDoListId);
        if (toDoList.isFinished()) {
            return null;
        }
        toDoList.setIsFinished(true);
        return this.toDoListRepository.save(toDoList);
    }

}
