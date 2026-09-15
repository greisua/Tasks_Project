package org.german.tasks.repository;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private DBConection connectionDB = new DBConection(
            "jdbc:postgresql://localhost:5432/tasks_project",
            "postgres",
            "admin"
    );


    public List<Project> findAll() {

        List<Project> ls = new ArrayList<>();

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM projects"
                );

                ResultSet result = statement.executeQuery()
        ) {

            System.out.println("Conexión realizada correctamente");

            while (result.next()) {

                Long id = result.getLong("id");
                String name = result.getString("name");
                String description = result.getString("description");

                LocalDate creationDate =
                        result.getDate("creationDate").toLocalDate();

                Project project = new Project(
                        id,
                        name,
                        description,
                        creationDate
                );

                ls.add(project);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }


    public Project findById(Long idProject) {

        Project project = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM projects WHERE id = ?"
                )
        ) {

            statement.setLong(1, idProject);

            ResultSet result = statement.executeQuery();

            System.out.println("Conexión realizada correctamente");

            if (result.next()) {

                Long id = result.getLong("id");
                String name = result.getString("name");
                String description = result.getString("description");

                LocalDate creationDate =
                        result.getDate("creationDate").toLocalDate();

                project = new Project(
                        id,
                        name,
                        description,
                        creationDate
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el proyecto");
            e.printStackTrace();
        }

        return project;
    }


    public Long create(Project project) {

        Long id = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO projects (name, description, creationDate) " +
                                "VALUES (?, ?, ?) RETURNING id"
                )
        ) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setObject(3, project.getCreationDate());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                id = result.getLong("id");
            }

            System.out.println("Proyecto creado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear el proyecto");
            e.printStackTrace();
        }

        return id;
    }


    // Método utilizado cuando la operación forma parte de una transacción
    public Long create(Project project, Connection connection) throws SQLException {

        Long id = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO projects (name, description, creationDate) " +
                                "VALUES (?, ?, ?) RETURNING id"
                )
        ) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setObject(3, project.getCreationDate());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                id = result.getLong("id");
            }

            System.out.println("Proyecto creado correctamente");

        }

        return id;
    }


    public void deleteById(Long idProject) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM projects WHERE id = ?"
                )
        ) {

            statement.setLong(1, idProject);

            statement.executeUpdate();

            System.out.println("Proyecto eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el proyecto");
            e.printStackTrace();
        }
    }


    public void update(Long id, String name, String description) {

        String sql = "UPDATE projects SET ";

        List<String> fields = new ArrayList<>();

        if (name != null) {
            fields.add("name = ?");
        }

        if (description != null) {
            fields.add("description = ?");
        }

        if (fields.isEmpty()) {
            System.out.println("No hay ningún campo para actualizar");
            return;
        }

        sql += String.join(", ", fields);
        sql += " WHERE id = ?";

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            int parameter = 1;

            if (name != null) {
                statement.setString(parameter++, name);
            }

            if (description != null) {
                statement.setString(parameter++, description);
            }

            statement.setLong(parameter, id);

            statement.executeUpdate();

            System.out.println("Proyecto actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar el proyecto");
            e.printStackTrace();
        }
    }

    public void updateLeader(Long idUser, Long idProject, boolean leader) {

        try (
                Connection connection = connectionDB.connectBBDD();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE users_projects " +
                                "SET leader = ? " +
                                "WHERE user_id = ? AND project_id = ?"
                )
        ) {

            statement.setBoolean(1, leader);
            statement.setLong(2, idUser);
            statement.setLong(3, idProject);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el responsable del proyecto");
            e.printStackTrace();
        }
    }
}