package com.tkn.smarttasks.dto.teams;

import java.util.List;
import java.util.UUID;

public record AddUsersToTheTeamRequest (
        UUID teamId,
        List<UUID> usersToAdd) {
}
