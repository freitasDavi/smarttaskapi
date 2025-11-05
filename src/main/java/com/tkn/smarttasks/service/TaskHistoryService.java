package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.Task;
import com.tkn.smarttasks.domain.TaskHistory;
import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.dto.tasks.UpdateTaskHistoryDTO;
import com.tkn.smarttasks.repository.TaskHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskHistoryService {

    private final TaskHistoryRepository taskRepository;

    public TaskHistoryService(TaskHistoryRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveNewChange(UpdateTaskHistoryDTO dto) {
        TaskHistory taskHistory = TaskHistory.builder()
                .changeType(dto.changeType())
                .oldValue(dto.oldValue())
                .newValue(dto.newValue())
                .task(Task.builder().id(dto.task()).build())
                .changedBy(User.builder().id(dto.changeUser()).build())
                .build();

        taskRepository.save(taskHistory);
    }
}
