package app.domain.services;

import app.domain.entities.Task;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TaskService {

    public static List<Task> tasks;

    public Mono<Task> insertTask(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Mono<List<Task>> listTasks(){
        return Mono.just(tasks);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .map(Task::newTask);
    }
}
