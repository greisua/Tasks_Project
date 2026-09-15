package org.german.tasks.service;

import org.german.tasks.model.User;
import org.german.tasks.repository.UserRepository;
public class UserService {

    private UserRepository userRepositiory = new UserRepository();

    public void createUser(User usuario){

        User existingUser = userRepositiory.findByEmail(usuario.getEmail());

        if (existingUser != null){
            System.out.println("El email ya existe");
        }else {
            userRepositiory.create(usuario);
        }
    }
}
