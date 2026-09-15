package org.german.tasks.repository;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.Task;
import org.german.tasks.utils.TaskPriority;
import org.german.tasks.utils.TaskStates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TaskRepository {

    private DBConection connectionDB = new DBConection(
            "jdbc:postgresql://localhost:5432/tasks_project",
            "postgres",
            "admin"
    );


    public List<Task> findAll() {

        List<Task> ls = new ArrayList<>();

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM tasks"
                );

                ResultSet result = statement.executeQuery()
        ) {

            System.out.println("Conexión realizada correctamente");

            while (result.next()) {

                Long id = result.getLong("id");
                String title = result.getString("title");

                TaskPriority priority =
                        TaskPriority.valueOf(result.getString("priority"));

                LocalDate creationDate =
                        result.getDate("creation_date").toLocalDate();

                LocalDate limitDate =
                        result.getDate("limit_date").toLocalDate();

                Long projectId = result.getLong("project_id");

                Long userId = result.getObject("user_id", Long.class);

                TaskStates status =
                        TaskStates.valueOf(result.getString("status"));

                Task task = new Task(
                        id,
                        title,
                        status,
                        priority,
                        creationDate,
                        limitDate,
                        projectId,
                        userId
                );

                ls.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }


    public Task findById(Long idTask) {

        Task task = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM tasks WHERE id = ?"
                )
        ) {

            statement.setLong(1, idTask);

            ResultSet result = statement.executeQuery();

            System.out.println("Conexión realizada correctamente");

            if (result.next()) {

                Long id = result.getLong("id");
                String title = result.getString("title");

                TaskPriority priority =
                        TaskPriority.valueOf(result.getString("priority"));

                LocalDate creationDate =
                        result.getDate("creation_date").toLocalDate();

                LocalDate limitDate =
                        result.getDate("limit_date").toLocalDate();

                Long projectId =
                        result.getLong("project_id");

                Long userId =
                        result.getObject("user_id", Long.class);

                TaskStates status =
                        TaskStates.valueOf(result.getString("status"));

                task = new Task(
                        id,
                        title,
                        status,
                        priority,
                        creationDate,
                        limitDate,
                        projectId,
                        userId
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar la tarea");
            e.printStackTrace();
        }

        return task;
    }


    public void create(Task task) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO tasks " +
                                "(title, priority, creation_date, limit_date, " +
                                "project_id, user_id, status) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)"
                )
        ) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getPriority().name());
            statement.setObject(3, task.getCreationDate());
            statement.setObject(4, task.getLimitDate());
            statement.setLong(5, task.getProjectId());

            if (task.getUserId() != null) {
                statement.setLong(6, task.getUserId());
            } else {
                statement.setNull(6, java.sql.Types.BIGINT);
            }

            statement.setString(7, task.getStatus().name());

            statement.executeUpdate();

            System.out.println("Tarea creada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear la tarea");
            e.printStackTrace();
        }
    }


    public void deleteById(Long idTask) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM tasks WHERE id = ?"
                )
        ) {

            statement.setLong(1, idTask);

            statement.executeUpdate();

            System.out.println("Tarea eliminada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar la tarea");
            e.printStackTrace();
        }
    }


    public void update(Long id, String title, TaskPriority priority,
                       LocalDate limitDate, Long userId,
                       TaskStates status) {

        String sql = "UPDATE tasks SET ";

        List<String> fields = new ArrayList<>();

        if (title != null) {
            fields.add("title = ?");
        }

        if (priority != null) {
            fields.add("priority = ?");
        }

        if (limitDate != null) {
            fields.add("limit_date = ?");
        }

        if (userId != null) {
            fields.add("user_id = ?");
        }

        if (status != null) {
            fields.add("status = ?");
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

            if (title != null) {
                statement.setString(parameter++, title);
            }

            if (priority != null) {
                statement.setString(parameter++, priority.name());
            }

            if (limitDate != null) {
                statement.setObject(parameter++, limitDate);
            }

            if (userId != null) {
                statement.setLong(parameter++, userId);
            }

            if (status != null) {
                statement.setString(parameter++, status.name());
            }

            statement.setLong(parameter, id);

            statement.executeUpdate();

            System.out.println("Tarea actualizada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar la tarea");
            e.printStackTrace();
        }
    }
}