package org.german;

import org.german.tasks.repository.UserRepository;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        String url = "jdbc:postgresql://localhost:5432/tasks_project";
        String usuario="postgres";
        String password="admin";
        UserRepository userRepository= new UserRepository();



        System.out.println(userRepository.findAll());


    }
}
