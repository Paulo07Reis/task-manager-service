package app.application.converters;

import app.application.dtos.TaskDto;
import app.domain.entities.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskConverter {

    public Task toDomain(TaskDto dto){
        return Optional.ofNullable(dto)
                .map(source -> Task.builder()
                        .withTitle(source.getTitle())
                        .withDescription(source.getDescription())
                        .withPriority(source.getPriority())
                        .withState(source.getStatus())
                        .build()
                )
                .orElse(null);
    }

    public TaskDto toDto(Task task){
        return Optional.ofNullable(task)
                .map(source -> {
                    TaskDto dto = new TaskDto();
                    dto.setTitle(source.getTitle());
                    dto.setDescription(source.getDescription());
                    dto.setPriority(source.getPriority());
                    dto.setStatus(source.getStatus());
                    return dto;
                })
                .orElse(null);
    }

    public List<TaskDto> convertList(List<Task> list){
        return Optional.ofNullable(list)
                .map(array -> array.stream().map(this::toDto).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
