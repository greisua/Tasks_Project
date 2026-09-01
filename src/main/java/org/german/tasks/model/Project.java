package org.german.tasks.model;

import java.time.LocalDate;
import java.util.Objects;

public class Project {
    private Long id;
    private String name;
    private String description;
    private LocalDate creationDate;

    public Project(Long id, String name, String description, LocalDate creationDate) {

        if (id == null) {
            throw new IllegalArgumentException("El campo 'id' no puede ser null");
        }
        if (name == null) {
            throw new IllegalArgumentException("El campo 'name' no puede ser null");
        }

        if (creationDate == null) {
            throw new IllegalArgumentException("El campo 'creationDate' no puede ser null");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Project project)) return false;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
