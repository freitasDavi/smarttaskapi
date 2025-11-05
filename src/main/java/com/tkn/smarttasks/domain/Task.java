package com.tkn.smarttasks.domain;

import com.tkn.smarttasks.domain.enums.TaskStatus;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name = "title", length = 300)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    private String priority;

    @Column(name = "due_data")
    private LocalDate dueDate;

    @Column(name = "metadata")
    @Type(JsonType.class)
    private Map<String, Object> metadata;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id",  nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    public Task(Task taskToCopy) {
        this.id = taskToCopy.getId();
        this.title = taskToCopy.getTitle();
        this.description = taskToCopy.getDescription();
        this.status = taskToCopy.getStatus();
        this.assignee = taskToCopy.getAssignee();
        this.priority = taskToCopy.getPriority();
        this.dueDate = taskToCopy.getDueDate();
        this.metadata = taskToCopy.getMetadata();
        this.createdAt = taskToCopy.getCreatedAt();
        this.updatedAt = taskToCopy.getUpdatedAt();
        this.team = taskToCopy.getTeam();
        this.reporter = taskToCopy.getReporter();
        this.assignee = taskToCopy.getAssignee();
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate void onUpdate() {
        this.updatedAt = Instant.now();
    }

}
