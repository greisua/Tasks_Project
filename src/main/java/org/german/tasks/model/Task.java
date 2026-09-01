package org.german.tasks.model;

import org.german.tasks.utils.TaskPriority;
import org.german.tasks.utils.TaskStates;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private Long id;
    private String title;
    private TaskStates status;
    private TaskPriority priority;
    private LocalDate creationDate;
    private LocalDate limitDate;
    private Long projectId;
    private Long userId = null;


    public Task(Long id, String title, TaskStates status, TaskPriority priority,
                LocalDate creationDate, LocalDate limitDate, Long projectId, Long userId) {

        if (id == null) {
            throw new IllegalArgumentException("El campo 'id' no puede ser null");
        }
        if (title == null) {
            throw new IllegalArgumentException("El campo 'title' no puede ser null");
        }
        if (status == null) {
            throw new IllegalArgumentException("El campo 'status' no puede ser null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("El campo 'priority' no puede ser null");
        }
        if (creationDate == null) {
            throw new IllegalArgumentException("El campo 'creationDate' no puede ser null");
        }
        if (limitDate == null || limitDate.isBefore(creationDate)) {
            throw new IllegalArgumentException("El campo 'limitDate' no puede ser null o anterior a el campo 'creationDate");
        }
        if (projectId == null) {
            throw new IllegalArgumentException("El campo 'projectId' no puede ser null");
        }

        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.creationDate = creationDate;
        this.limitDate = limitDate;
        this.projectId = projectId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStates getStatus() {
        return status;
    }

    public void setStatus(TaskStates status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }


    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", creationDate=" + creationDate +
                ", limitDate=" + limitDate +
                ", projectId=" + projectId +
                ", userId=" + userId +
                '}';
    }
}


