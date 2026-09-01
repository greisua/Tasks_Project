package org.german.tasks.model;

import java.time.LocalDate;
import java.util.Objects;

public class Comment {

    private Long id;
    private String content;
    private LocalDate date;
    private Long userId;
    private Long taskId;

    public Comment(Long id, String content, LocalDate date, Long userId, Long taskId) {

        if (id == null) {
            throw new IllegalArgumentException("El campo 'id' no puede ser null");
        }
        if (content == null) {
            throw new IllegalArgumentException("El campo 'content' no puede ser null");
        }
        if (date == null) {
            throw new IllegalArgumentException("El campo 'date' no puede ser null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("El campo 'userId' no puede ser null");
        }
        if (taskId == null) {
            throw new IllegalArgumentException("El campo 'taskId' no puede ser null");
        }

        this.id = id;
        this.content = content;
        this.date = date;
        this.userId = userId;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }


    public String getContent() {
        return content;
    }


    public LocalDate getDate() {
        return date;
    }


    public Long getUserId() {
        return userId;
    }


    public Long getTaskId() {
        return taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", taskId=" + taskId +
                '}';
    }
}
