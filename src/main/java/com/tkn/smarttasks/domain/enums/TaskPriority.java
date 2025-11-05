package com.tkn.smarttasks.domain.enums;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String shortName;

    TaskPriority(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}
