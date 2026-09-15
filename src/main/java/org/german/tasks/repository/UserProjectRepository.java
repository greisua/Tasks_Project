package org.german.tasks.repository;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.UserProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProjectRepository {

    private DBConection connectionDB = new DBConection(
            "jdbc:postgresql://localhost:5432/tasks_project",
            "postgres",
            "admin"
    );


    public List<UserProject> findAll() {

        List<UserProject> ls = new ArrayList<>();

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM users_projects"
                );

                ResultSet result = statement.executeQuery()
        ) {

            System.out.println("Conexión realizada correctamente");

            while (result.next()) {

                Long idUser = result.getLong("user_id");
                Long idProject = result.getLong("project_id");
                boolean leader = result.getBoolean("leader");

                UserProject userProject =
                        new UserProject(idUser, idProject, leader);

                ls.add(userProject);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }


    public UserProject findById(Long idUser, Long idProject) {

        UserProject userProject = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM users_projects " +
                                "WHERE user_id = ? AND project_id = ?"
                )
        ) {

            statement.setLong(1, idUser);
            statement.setLong(2, idProject);

            ResultSet result = statement.executeQuery();

            System.out.println("Conexión realizada correctamente");

            if (result.next()) {

                Long userId = result.getLong("user_id");
                Long projectId = result.getLong("project_id");
                boolean leader = result.getBoolean("leader");

                userProject =
                        new UserProject(userId, projectId, leader);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar la relación usuario-proyecto");
            e.printStackTrace();
        }

        return userProject;
    }


    public void create(UserProject userProject) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users_projects " +
                                "(user_id, project_id, leader) " +
                                "VALUES (?, ?, ?)"
                )
        ) {

            statement.setLong(1, userProject.getIdUser());
            statement.setLong(2, userProject.getIdProject());
            statement.setBoolean(3, userProject.isLeader());

            statement.executeUpdate();

            System.out.println("Relación usuario-proyecto creada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear la relación usuario-proyecto");
            e.printStackTrace();
        }
    }


    // Método utilizado cuando la operación forma parte de una transacción
    public void create(UserProject userProject, Connection connection) throws SQLException {

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users_projects " +
                                "(user_id, project_id, leader) " +
                                "VALUES (?, ?, ?)"
                )
        ) {

            statement.setLong(1, userProject.getIdUser());
            statement.setLong(2, userProject.getIdProject());
            statement.setBoolean(3, userProject.isLeader());

            statement.executeUpdate();

            System.out.println("Relación usuario-proyecto creada correctamente");

        }
    }


    public void deleteById(Long idUser, Long idProject) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM users_projects " +
                                "WHERE user_id = ? AND project_id = ?"
                )
        ) {

            statement.setLong(1, idUser);
            statement.setLong(2, idProject);

            statement.executeUpdate();

            System.out.println("Relación usuario-proyecto eliminada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar la relación usuario-proyecto");
            e.printStackTrace();
        }
    }


    public void update(Long idUser, Long idProject, Boolean leader) {

        if (leader == null) {
            System.out.println("No hay ningún campo para actualizar");
            return;
        }

        String sql = "UPDATE users_projects SET leader = ? " +
                "WHERE user_id = ? AND project_id = ?";

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setBoolean(1, leader);
            statement.setLong(2, idUser);
            statement.setLong(3, idProject);

            statement.executeUpdate();

            System.out.println("Relación usuario-proyecto actualizada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar la relación usuario-proyecto");
            e.printStackTrace();
        }
    }


}