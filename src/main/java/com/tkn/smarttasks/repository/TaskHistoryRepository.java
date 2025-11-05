package com.tkn.smarttasks.repository;

import com.tkn.smarttasks.domain.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    List<TaskHistory> findAllByTask_Id(UUID taskId);
}
