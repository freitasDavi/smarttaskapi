package com.tkn.smarttasks.controller;

import com.tkn.smarttasks.dto.tasks.NewTaskRequest;
import com.tkn.smarttasks.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity createTask (@RequestBody  NewTaskRequest task) {
        try {
            String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            taskService.newTask(task, userEmail);

            return ResponseEntity.ok().build();
        } catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
