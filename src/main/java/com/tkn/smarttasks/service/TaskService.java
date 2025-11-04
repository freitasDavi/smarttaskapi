package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.Task;
import com.tkn.smarttasks.domain.Team;
import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.dto.tasks.NewTaskRequest;
import com.tkn.smarttasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public void newTask(NewTaskRequest request, String email) {
        var userData = userService.getUserDataByEmail(email);

        Task newTask = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .team(Team.builder().id(request.team()).build())
                .assignee(
                        request.assignee().isEmpty() ? null
                                :
                        User.builder()
                            .id(request.assignee().get())
                            .build()
                )
                .reporter(User.builder()
                        .id(
                            request.reporter().orElseGet(userData::getId)
                        )
                        .build())
                .build();

        taskRepository.saveAndFlush(newTask);
    }
}
