package com.tkn.smarttasks.dto.tasks;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record NewTaskRequest(
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate,

        UUID team,
        Optional<UUID> assignee,
        Optional<UUID> reporter
) {
}
