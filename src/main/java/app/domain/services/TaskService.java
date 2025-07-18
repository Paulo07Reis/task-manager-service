package app.domain.services;

import app.domain.entities.Task;
import java.util.ArrayList;
import java.util.List;
import app.resource.repositories.TaskCustomRepository;
import app.resource.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    public static List<Task> tasks = new ArrayList<>();

    private final TaskRepository repository;
    private final TaskCustomRepository customRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repository, TaskCustomRepository taskCustomRepository){
        this.repository = repository;
        this.customRepository = taskCustomRepository;
    }

    public Mono<Task> insertTask(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save)
                .doOnError(error -> LOGGER.error("Error during save task. Title {}", task.getTitle(), error))
                .onErrorResume(it -> Mono.just(Task.builder().withTitle("Error").build()));
    }

    public Page<Task> findPaginated(Task task, Integer pageNumber, Integer pageSize){
        return customRepository.findPaginated(task, pageNumber, pageSize);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .doOnNext(t -> LOGGER.info("Saving task with title {}", t.getTitle()))
                .map(repository::save);
    }

    public Mono<Void> deleteById(String id){
        return Mono.fromRunnable(() -> repository.deleteById(id));
    }
}
