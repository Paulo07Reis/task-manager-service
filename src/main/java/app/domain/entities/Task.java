package app.domain.entities;

import app.domain.enums.Status;
import app.domain.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String title;
    private String description;
    private int priority;
    private Status status;

    public Task(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.status = builder.status;
    }

    public Task newTask(){
        TaskService.tasks.add(this);
        return this;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Builder builderFrom(Task task){
        return new Builder(task);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Builder {
        private String title;
        private String description;
        private int priority;
        private Status status;

        public Builder(Task task) {
            this.title = task.title;
            this.description = task.description;
            this.priority = task.priority;
            this.status = task.status;
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public Builder withPriority(int priority){
            this.priority = priority;
            return this;
        }

        public Builder withState(Status status){
            this.status = status;
            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }
}
