package org.german.tasks.model;

import java.util.Objects;

public class UserProject {

    private Long idUser;
    private Long idProject;
    private boolean leader;

    public UserProject(Long idUser, Long idProject, Boolean leader) {

        if (idUser == null) {
            throw new IllegalArgumentException("El campo 'idUser' no puede ser null");
        }
        if (idProject == null) {
            throw new IllegalArgumentException("El campo 'idProject' no puede ser null");
        }
        if (leader == null) {
            throw new IllegalArgumentException("El campo 'leader' no puede ser null");
        }

        this.idUser = idUser;
        this.idProject = idProject;
        this.leader = leader;
    }

    public Long getIdUser() {
        return idUser;
    }


    public Long getIdProject() {
        return idProject;
    }


    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserProject that)) return false;
        return Objects.equals(idUser, that.idUser) && Objects.equals(idProject, that.idProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idProject);
    }

    @Override
    public String toString() {
        return "UserProject{" +
                "idUser=" + idUser +
                ", idProject=" + idProject +
                ", leader=" + leader +
                '}';
    }
}
