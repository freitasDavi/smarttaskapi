package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.Team;
import com.tkn.smarttasks.domain.TeamMember;
import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.dto.teams.AddUsersToTheTeamRequest;
import com.tkn.smarttasks.dto.teams.CreateTeamRequest;
import com.tkn.smarttasks.dto.teams.GetUserTeamsResponse;
import com.tkn.smarttasks.repository.TeamRepository;
import com.tkn.smarttasks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public void createTeam (CreateTeamRequest request, String userEmail) {
        var owner = userService.getUserDataByEmail(userEmail);

        Team team = Team.builder()
                .owner(owner)
                .name(request.name())
                .members(new ArrayList<>())
                .build();

        TeamMember member = TeamMember.builder()
                .user(owner)
                .team(team)
                .role("Admin")
                .build();

        team.getMembers().add(member);

        teamRepository.save(team);
    }

    public List<GetUserTeamsResponse> findAllTeamsByOwnerId(String userEmail) {
        var owner = userService.getUserDataByEmail(userEmail);

        return teamRepository.findAllByOwnerId(owner.getId())
                .stream()
                .map(team -> new GetUserTeamsResponse(
                    team.getId(),
                    team.getName(),
                    team.getCreatedAt()
                )).toList();
    }

    public void addUsersToTheTeam (AddUsersToTheTeamRequest request) {
        var team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        var usersToAdd = request.usersToAdd().stream().map(
                userId -> TeamMember.builder().user(
                        User.builder()
                            .id(userId)
                            .build()
                )
                    .role("Member")
                    .team(team)
                    .build()
        ).toList();

        team.getMembers().addAll(usersToAdd);

        teamRepository.save(team);
    }
}
