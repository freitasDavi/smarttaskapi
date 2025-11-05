package com.tkn.smarttasks.domain.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    BLOCKED("Blocked"),
    DONE("Done"),
    INPROGRESS("In progress"),
    TODO("ToDo");

    private final String shortName;

    TaskStatus(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}
