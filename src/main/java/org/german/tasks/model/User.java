package org.german.tasks.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String surname1;
    private String surname2;
    private String email;


    public User(Long id, String name, String surname1, String surname2, String email) {

        if (id == null) {
            throw new IllegalArgumentException("El campo 'id' no puede ser null");
        }
        if (name == null) {
            throw new IllegalArgumentException("El campo 'name' no puede ser null");
        }
        if (surname1 == null) {
            throw new IllegalArgumentException("El campo 'surname1' no puede ser null");
        }
        if (email == null) {
            throw new IllegalArgumentException("El campo 'email' no puede ser null");
        }

        this.id = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.email = email;
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

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname1='" + surname1 + '\'' +
                ", surname2='" + surname2 + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
