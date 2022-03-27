package com.example.entreprise.web;

import com.example.entreprise.model.ToDoList;
import com.example.entreprise.kafka.ProducerKafka;
import com.example.entreprise.service.ToDoListService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ToDoListRessource {


    private ProducerKafka producerKafka;
    private BeanFactory beanFactory;

    public ToDoListRessource(BeanFactory beanFactory) {
        producerKafka = new ProducerKafka();
        this.beanFactory = beanFactory;
    }

    @GetMapping("/toDoList/{toDoListId}")
    public ResponseEntity<ToDoList> findToDoListById(@PathVariable("toDoListId") String toDoListId) {

        ToDoList toDoList = this.beanFactory.getBean(ToDoListService.class).findToDoListById(toDoListId);

        if (toDoList == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(toDoList);
    }

    @GetMapping("/toDoList")
    public ResponseEntity<List<ToDoList>> findAllToDoList() {

        List<ToDoList> toDoLists = beanFactory.getBean(ToDoListService.class).findAllToDoList();

        return ResponseEntity.ok().body(toDoLists);
    }

    @PostMapping("/toDoList")
    public ResponseEntity<ToDoList> insertToDoList(@RequestBody ToDoList newToDoList) {
        ToDoList toDoList = beanFactory.getBean(ToDoListService.class)
                .insertToDoList(new ToDoList(newToDoList.getTitle(), newToDoList.getDescription(),
                        new ArrayList<String>(), false, newToDoList.getTeamNumber()));
        producerKafka.sendMessage(toDoList.getTeamNumber(), "The project " + toDoList.getTitle() + " has been started");
        return ResponseEntity.ok().body(toDoList);
    }

    @PutMapping("/toDoList/{toDoListId}")
    public ResponseEntity<ToDoList> addEmployeeToToDoList(@PathVariable("toDoListId") String toDoListId, @RequestBody Map<String, String> employeeEmail) {
        ToDoList toDoList = beanFactory.getBean(ToDoListService.class)
                .addEmployeeToToDoList(toDoListId, employeeEmail.get("employeeEmail"));
        if (toDoList == null) {
            return ResponseEntity.notFound().build();
        }
        producerKafka.sendMessage(toDoList.getTeamNumber(), employeeEmail.get("employeeEmail") + " has been added to the project : " + toDoList.getTitle());
        return ResponseEntity.ok().body(toDoList);
    }

    @PutMapping("/toDoList/{toDoListId}/finish")
    public ResponseEntity<ToDoList> finishToDoList(@PathVariable("toDoListId") String toDoListId) {
        ToDoList toDoList = beanFactory.getBean(ToDoListService.class).finishToDoList(toDoListId);
        if (toDoList == null) {
            return ResponseEntity.notFound().build();
        }
        producerKafka.sendMessage(toDoList.getTeamNumber(), "The project " + toDoList.getTitle() + " is finished");
        return ResponseEntity.ok().body(toDoList);
    }
}