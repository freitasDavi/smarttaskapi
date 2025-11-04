package com.tkn.smarttasks.dto.teams;

import java.time.Instant;
import java.util.UUID;


public record GetUserTeamsResponse (UUID teamId, String name, Instant createdAt) {
}
