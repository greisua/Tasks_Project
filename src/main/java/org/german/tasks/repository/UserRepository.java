package org.german.tasks.repository;

import org.german.tasks.config.DBConection;
import org.german.tasks.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private DBConection connectionDB = new DBConection("jdbc:postgresql://localhost:5432/tasks_project", "postgres", "admin");


    public List<User> findAll() {

        List<User> ls = new ArrayList<>();
        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

                ResultSet result = statement.executeQuery();) {

            System.out.println("Conexión realizada correctamente");
            while (result.next()) {

                Long id = result.getLong("id");
                String name = result.getString("name");
                String surname1 = result.getString("surname1");
                String surname2 = result.getString("surname2");
                String email = result.getString("email");


                User user = new User(id, name, surname1, surname2, email);

                ls.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }

    public User findById(int id_user) {

        User user = null;

        try (
                Connection connection = connectionDB.connectBBDD();

                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id= ?");


        )

        {
            statement.setInt(1,id_user);

            ResultSet result = statement.executeQuery();
            System.out.println("Conexión realizada correctamente");

            result.next();
            Long id = result.getLong("id");
            String name = result.getString("name");
            String surname1 = result.getString("surname1");
            String surname2 = result.getString("surname2");
            String email = result.getString("email");
            user = new User(id, name, surname1, surname2, email);



        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
        return user;

    }


    public void create(User user) {

        try (
                Connection connection = connectionDB.connectBBDD();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users (name, surname1, surname2, email) " +
                                "VALUES (?, ?, ?, ?)"
                )
        ) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname1());
            statement.setString(3, user.getSurname2());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();

            System.out.println("Usuario creado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear el usuario");
            e.printStackTrace();
        }
    }

    public void deleteById(Long id_user) {

        try (
                Connection connection = connectionDB.connectBBDD();
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM users WHERE id = ?"
                )
        ) {

            statement.setLong(1, id_user);

            statement.executeUpdate();

            System.out.println("Usuario eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario");
            e.printStackTrace();
        }
    }

    public void update(Long id, String name, String surname1,
                       String surname2, String email) {

        String sql = "UPDATE users SET ";

        List<String> fields = new ArrayList<>();

        if (name != null) {
            fields.add("name = ?");
        }

        if (surname1 != null) {
            fields.add("surname1 = ?");
        }

        if (surname2 != null) {
            fields.add("surname2 = ?");
        }

        if (email != null) {
            fields.add("email = ?");
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

            if (surname1 != null) {
                statement.setString(parameter++, surname1);
            }

            if (surname2 != null) {
                statement.setString(parameter++, surname2);
            }

            if (email != null) {
                statement.setString(parameter++, email);
            }

            statement.setLong(parameter, id);

            statement.executeUpdate();

            System.out.println("Usuario actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario");
            e.printStackTrace();
        }
    }

    public User findByEmail(String email) {

        User user = null;

        try (
                Connection connection = connectionDB.connectBBDD();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM users WHERE email = ?"
                )
        ) {

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            System.out.println("Conexión realizada correctamente");

            if (result.next()) {

                Long id = result.getLong("id");
                String name = result.getString("name");
                String surname1 = result.getString("surname1");
                String surname2 = result.getString("surname2");
                String userEmail = result.getString("email");

                user = new User(id, name, surname1, surname2, userEmail);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario por email");
            e.printStackTrace();
        }

        return user;
    }



}
