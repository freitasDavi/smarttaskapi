package com.tkn.smarttasks.domain;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class TeamMemberPK implements Serializable {

    private UUID team;
    private UUID user;

}
