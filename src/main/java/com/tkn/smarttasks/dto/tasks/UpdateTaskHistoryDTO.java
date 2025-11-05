package com.tkn.smarttasks.dto.tasks;

import com.tkn.smarttasks.domain.enums.TaskChangeType;

import java.util.UUID;

public record UpdateTaskHistoryDTO (
        UUID task,
        UUID changeUser,
        TaskChangeType changeType,
        String oldValue,
        String newValue
         )
{
}
