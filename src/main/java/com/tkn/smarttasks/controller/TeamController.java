package com.tkn.smarttasks.controller;

import com.tkn.smarttasks.dto.teams.AddUsersToTheTeamRequest;
import com.tkn.smarttasks.dto.teams.CreateTeamRequest;
import com.tkn.smarttasks.dto.teams.GetUserTeamsResponse;
import com.tkn.smarttasks.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/team")
@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Void> createTeam (@RequestBody CreateTeamRequest request)
    {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        teamService.createTeam(request, userEmail);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetUserTeamsResponse>> findAllTeamsByOwnerId() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok().body(teamService.findAllTeamsByOwnerId(userEmail));
    }

    @PostMapping("/members")
    public ResponseEntity addNewMembers (@RequestBody AddUsersToTheTeamRequest request) {
        try {
            teamService.addUsersToTheTeam(request);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
