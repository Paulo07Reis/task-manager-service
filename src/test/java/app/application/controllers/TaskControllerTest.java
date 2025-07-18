package app.application.controllers;

import app.application.converters.TaskConverter;
import app.application.dtos.TaskDto;
import app.domain.entities.Task;
import app.domain.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskService service;

    @Mock
    private TaskConverter converter;

    @Test
    public void controller_must_return_ok_when_save_successfully() {
        when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());
        when(service.insertTask(any())).thenReturn(Mono.just(new Task()));

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new TaskDto())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class);
    }
}
