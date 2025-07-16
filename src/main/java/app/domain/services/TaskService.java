package app.domain.services;

import app.domain.entities.Task;
import java.util.ArrayList;
import java.util.List;
import app.resource.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    public static List<Task> tasks = new ArrayList<>();

    @Autowired
    private TaskRepository repository;

    public Mono<Task> insertTask(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Mono<List<Task>> listTasks(){
        return Mono.just(repository.findAll());
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .map(repository::save);
    }
}
