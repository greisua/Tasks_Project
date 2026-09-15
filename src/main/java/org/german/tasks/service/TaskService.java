package org.german.tasks.service;

import org.german.tasks.model.Task;
import org.german.tasks.model.UserProject;
import org.german.tasks.repository.ProjectRepository;
import org.german.tasks.repository.TaskRepository;
import org.german.tasks.repository.UserProjectRepository;
import org.german.tasks.utils.TaskPriority;
import org.german.tasks.utils.TaskStates;

import java.sql.SQLException;
import java.time.LocalDate;

public class TaskService {

    private TaskRepository taskRepository;
    private UserProjectRepository userProjectRepository;
    private ProjectRepository projectRepositopry;

    public void createTask (Task task) {

        Long iduser = task.getUserId();
        Long idproject = task.getProjectId();


        if(projectRepositopry.findById(idproject) == null){
            System.out.println("El proyecto no existe");

        }else if (iduser ==null){
            try {
                taskRepository.create(task);
                System.out.println("Tarea Creada");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {

            UserProject userProject = userProjectRepository.findById(iduser,idproject);

            if (userProject == null){

                System.out.println("El usuario no pertenece al proyecto");

            }else{

                taskRepository.create(task);
                System.out.println("Tarea Creada");

            }
        }


    }

    public void updateTask (Long id, String title, TaskPriority priority,
                            LocalDate limitDate, Long userId,
                            TaskStates status){

        TaskStates st = taskRepository.findById(id).getStatus();

        if(status == TaskStates.COMPLETED){
            taskRepository.update(id, title, priority, limitDate, userId, status);
        }else if(st==TaskStates.COMPLETED && status != TaskStates.COMPLETED) {
            System.out.println("La tarea no puede ser modificada");
        }


    }
}
