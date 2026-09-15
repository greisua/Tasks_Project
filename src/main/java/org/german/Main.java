package org.german;

import org.german.tasks.model.User;
import org.german.tasks.repository.UserRepository;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



        UserRepository userRepository= new UserRepository();



        //System.out.println(userRepository.findAll());

        //System.out.println(userRepository.findById(99));

        User usuario = new User(1l,"juan","perez",null,"jperez@gmail.com");

        //userRepository.create(usuario);

        userRepository.deleteById(1l);


    }
}
