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
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("tasks", taskService.getUserTasks(user));
        model.addAttribute("goals", goalService.getUserGoals(user));
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        task.setUser(user);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        task.setStatus("COMPLETED");
        taskService.updateTask(task);
        return "redirect:/tasks";
    }
}