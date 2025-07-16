package app.application.controllers;

import app.application.converters.TaskConverter;
import app.application.dtos.TaskDto;
import app.domain.enums.Status;
import app.domain.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<TaskDto> getTasks(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "0") int priority,
            @RequestParam(required = false) Status status,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return service.findPaginated(
                converter.convert(id, title, description, priority, status),
                pageNumber,
                pageSize
        ).map(converter::toDto);
    }

    @PostMapping
    public Mono<TaskDto> createTak(
            @RequestBody TaskDto task
    ){
        return service.insertTask(converter.toDomain(task))
                .map(converter::toDto);
    }
}
