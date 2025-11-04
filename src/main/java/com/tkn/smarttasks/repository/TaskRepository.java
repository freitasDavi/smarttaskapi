package com.tkn.smarttasks.repository;

import com.tkn.smarttasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
