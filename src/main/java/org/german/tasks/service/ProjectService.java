package org.german.tasks.service;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.Project;
import org.german.tasks.model.User;
import org.german.tasks.model.UserProject;
import org.german.tasks.repository.ProjectRepository;
import org.german.tasks.repository.UserProjectRepository;
import org.german.tasks.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class ProjectService {
    private ProjectRepository projectRepositiory = new  ProjectRepository();
    private UserRepository userRepositiory = new UserRepository();
    private UserProjectRepository userProjectRepository = new UserProjectRepository();
    private DBConection connectionDB = new DBConection(
            "jdbc:postgresql://localhost:5432/tasks_project",
            "postgres",
            "admin"
    );

    public void createProject(Project proyecto, String email) {

        User usuarioProject = userRepositiory.findByEmail(email);

        if (usuarioProject != null) {

            Connection connection = null;

            try {

                connection = connectionDB.connectBBDD();

                connection.setAutoCommit(false);

                Long idProject = projectRepositiory.create(
                        proyecto,
                        connection
                );

                userProjectRepository.create(
                        new UserProject(
                                usuarioProject.getId(),
                                idProject,
                                true
                        ),
                        connection
                );

                connection.commit();

                System.out.println("Proyecto creado correctamente");

            } catch (SQLException e) {

                if (connection != null) {
                    try {
                        connection.rollback();
                        System.out.println("Se ha realizado rollback");
                    } catch (SQLException rollbackException) {
                        rollbackException.printStackTrace();
                    }
                }

                e.printStackTrace();

            } finally {

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            System.out.println("El usuario no existe");
        }
    }


}
