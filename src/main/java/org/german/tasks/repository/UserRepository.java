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

    private DBConection connectionDB = new DBConection("jdbc:postgresql://localhost:5432/tasks_project","postgres","admin");



    public List<User> findAll(){

        List<User> ls = new ArrayList<>();
        try (
            Connection connection = connectionDB.connectBBDD();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

            ResultSet result = statement.executeQuery();)
        {

            System.out.println("Conexión realizada correctamente");
            while (result.next()) {

                Long id =result.getLong("id");
                String name = result.getString("name");
                String surname1 = result.getString("surname1");
                String surname2 = result.getString("surname2");
                String email = result.getString("email");


                User user = new User(id,name,surname1,surname2,email);

                ls.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return ls;
    }

}
