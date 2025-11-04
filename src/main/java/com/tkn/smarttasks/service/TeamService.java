package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.Team;
import com.tkn.smarttasks.dto.teams.CreateTeamRequest;
import com.tkn.smarttasks.dto.teams.GetUserTeamsResponse;
import com.tkn.smarttasks.repository.TeamRepository;
import com.tkn.smarttasks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public void createTeam (CreateTeamRequest request, String userEmail) {
        var owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var team = Team.builder()
                .name(request.name())
                .owner(owner)
                .build();

        teamRepository.saveAndFlush(team);
    }

    public List<GetUserTeamsResponse> findAllTeamsByOwnerId(String userEmail) {
        var owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));


        return teamRepository.findAllByOwnerId(owner.getId())
                .stream()
                .map(team -> new GetUserTeamsResponse(
                    team.getId(),
                    team.getName(),
                    team.getCreatedAt()
                )).toList();
    }
}
