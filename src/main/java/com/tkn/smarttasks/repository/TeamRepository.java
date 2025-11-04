package com.tkn.smarttasks.repository;

import com.tkn.smarttasks.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findAllByOwnerId(UUID ownerId);
}
