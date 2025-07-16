package app.application.controllers;

import app.application.converters.TaskConverter;
import app.application.dtos.TaskDto;
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

    @Autowired
    private TaskConverter converter;

    @GetMapping
    public Mono<List<TaskDto>> getTasks() {
        return service.listTasks().map(converter::convertList);
    }

    @PostMapping
    public Mono<TaskDto> createTak(
            @RequestBody TaskDto task
    ){
        return service.insertTask(converter.toDomain(task))
                .map(converter::toDto);
    }
}
