package app.application.controllers;

import app.application.converters.TaskConverter;
import app.application.dtos.TaskDto;
import app.domain.enums.Status;
import app.domain.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;
    private final TaskConverter converter;

    public TaskController(TaskService service, TaskConverter converter){
        this.service = service;
        this.converter = converter;
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(
            @PathVariable String id
    ){
        return Mono.just(id)
                .flatMap(service::deleteById);
    }
}
