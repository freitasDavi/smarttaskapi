package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.Task;
import com.tkn.smarttasks.domain.Team;
import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.domain.enums.TaskChangeType;
import com.tkn.smarttasks.dto.tasks.NewTaskRequest;
import com.tkn.smarttasks.dto.tasks.UpdateStatusRequest;
import com.tkn.smarttasks.dto.tasks.UpdateTaskHistoryDTO;
import com.tkn.smarttasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskHistoryService taskHistoryService;

    public TaskService(TaskRepository taskRepository, UserService userService,  TaskHistoryService taskHistoryService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskHistoryService = taskHistoryService;
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

    public void updateStatus(UpdateStatusRequest request, String email)
    {
        Task taskToUpdate = taskRepository.findById(request.taskId())
                .orElseThrow(() -> new RuntimeException("No task found with id: " + request.taskId()));

        var requestUser =  userService.getUserDataByEmail(email);

        var updatedTask = new Task(taskToUpdate);

        updatedTask.setStatus(request.status());

        taskHistoryService.saveNewChange(new UpdateTaskHistoryDTO(
                request.taskId(),
                requestUser.getId(),
                TaskChangeType.UPDATE_STATUS,
                taskToUpdate.toString(),
                updatedTask.toString()
                ));

        taskRepository.saveAndFlush(updatedTask);
    }
}
