package org.german.tasks.repository;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    private DBConection connectionDB = new DBConection(
            "jdbc:postgresql://localhost:5432/tasks_project",
            "postgres",
            "admin"
    );


    public List<Comment> findAll() {

        List<Comment> ls = new ArrayList<>();

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM comments"
                );

                ResultSet result = statement.executeQuery()
        ) {

            System.out.println("Conexión realizada correctamente");

            while (result.next()) {

                Long id = result.getLong("id");
                String content = result.getString("content");

                LocalDate date =
                        result.getDate("date").toLocalDate();

                Long userId = result.getLong("user_id");
                Long taskId = result.getLong("task_id");

                Comment comment = new Comment(
                        id,
                        content,
                        date,
                        userId,
                        taskId
                );

                ls.add(comment);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }


    public Comment findById(Long idComment) {

        Comment comment = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM comments WHERE id = ?"
                )
        ) {

            statement.setLong(1, idComment);

            ResultSet result = statement.executeQuery();

            System.out.println("Conexión realizada correctamente");

            if (result.next()) {

                Long id = result.getLong("id");
                String content = result.getString("content");

                LocalDate date =
                        result.getDate("date").toLocalDate();

                Long userId = result.getLong("user_id");
                Long taskId = result.getLong("task_id");

                comment = new Comment(
                        id,
                        content,
                        date,
                        userId,
                        taskId
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el comentario");
            e.printStackTrace();
        }

        return comment;
    }


    public void create(Comment comment) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO comments " +
                                "(content, date, user_id, task_id) " +
                                "VALUES (?, ?, ?, ?)"
                )
        ) {

            statement.setString(1, comment.getContent());
            statement.setObject(2, comment.getDate());
            statement.setLong(3, comment.getUserId());
            statement.setLong(4, comment.getTaskId());

            statement.executeUpdate();

            System.out.println("Comentario creado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear el comentario");
            e.printStackTrace();
        }
    }


    public void deleteById(Long idComment) {

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM comments WHERE id = ?"
                )
        ) {

            statement.setLong(1, idComment);

            statement.executeUpdate();

            System.out.println("Comentario eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el comentario");
            e.printStackTrace();
        }
    }


    public void update(Long id, String content) {

        if (content == null) {
            System.out.println("No hay ningún campo para actualizar");
            return;
        }

        String sql = "UPDATE comments SET content = ? WHERE id = ?";

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, content);
            statement.setLong(2, id);

            statement.executeUpdate();

            System.out.println("Comentario actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar el comentario");
            e.printStackTrace();
        }
    }
}
