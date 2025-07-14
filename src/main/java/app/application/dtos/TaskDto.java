package app.application.dtos;

import app.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;
    private String description;
    private int priority;
    private Status status;
}
