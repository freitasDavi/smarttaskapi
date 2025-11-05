package com.tkn.smarttasks.domain.enums;

import lombok.Getter;

@Getter
public enum TaskChangeType {
    ADD_COMMENT("Added a comment"),
    REMOVE_COMMENT("Removed a comment"),
    UPDATE_TEXT("Updated text"),
    NEW_ASSIGNEE("New Assignee"),
    UPDATE_STATUS("Update task status"),
    UPDATE_PRIORITY("Update task priority"),;

    private final String shortName;

    TaskChangeType(String shortName) {
        this.shortName = shortName;
    }

}
