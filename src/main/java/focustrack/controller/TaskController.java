package focustrack.controller;

import focustrack.entity.Task;
import focustrack.entity.User;
import focustrack.service.GoalService;
import focustrack.service.TaskService;
import focustrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoalService goalService;

    @GetMapping
    public String tasks(Model model, Principal principal) {
        try {
            User user = userService.findByEmail(principal.getName());
            model.addAttribute("tasks", taskService.getUserTasks(user));
            model.addAttribute("goals", goalService.getUserGoals(user));
            model.addAttribute("newTask", new Task());
        } catch (Exception e) {
            model.addAttribute("tasks", new ArrayList<>());
            model.addAttribute("goals", new ArrayList<>());
            model.addAttribute("newTask", new Task());
        }
        return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task,
                             Principal principal) {
        try {
            User user = userService.findByEmail(principal.getName());
            task.setUser(user);
            taskService.createTask(task);
        } catch (Exception e) {
            System.out.println("Error creating task: " + e.getMessage());
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
        } catch (Exception e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            task.setStatus("COMPLETED");
            taskService.updateTask(task);
        } catch (Exception e) {
            System.out.println("Error completing task: " + e.getMessage());
        }
        return "redirect:/tasks";
    }
}

