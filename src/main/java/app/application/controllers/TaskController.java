package app.application.controllers;

import app.domain.entities.Task;
import app.domain.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public Mono<List<Task>> getTasks() {
        return service.listTasks();
    }

    @PostMapping
    public Mono<Task> createTak(
            @RequestBody Task task
    ){
        return service.insertTask(task);
    }
}
