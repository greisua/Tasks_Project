package org.german.tasks.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConection {
    private  String url = "jdbc:postgresql://localhost:5432/tasks_project";
    private String usuario="postgres";
    private String password="admin";

    public DBConection(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Connection connectBBDD() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }
}
