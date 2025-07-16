package app.domain.services;

import app.domain.entities.Task;
import java.util.ArrayList;
import java.util.List;

import app.resource.repositories.TaskCustomRepository;
import app.resource.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    public static List<Task> tasks = new ArrayList<>();

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskCustomRepository customRepository;

    public Mono<Task> insertTask(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Page<Task> findPaginated(Task task, Integer pageNumber, Integer pageSize){
        return customRepository.findPaginated(task, pageNumber, pageSize);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .map(repository::save);
    }
}
