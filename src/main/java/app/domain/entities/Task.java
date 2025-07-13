package app.domain.entities;

import app.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String title;
    private String description;
    private int priority;
    private Status status;
}
