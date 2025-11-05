package com.tkn.smarttasks.dto.tasks;

import com.tkn.smarttasks.domain.enums.TaskStatus;

import java.util.UUID;

public record UpdateStatusRequest (TaskStatus status, UUID taskId) {
}
