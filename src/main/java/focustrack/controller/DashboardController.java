package focustrack.controller;

import focustrack.entity.User;
import focustrack.service.GoalService;
import focustrack.service.TaskService;
import focustrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        try {
            User user = userService.findByEmail(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("goals", goalService.getUserGoals(user));
            model.addAttribute("tasks", taskService.getUserTasks(user));
            model.addAttribute("pendingTasks", taskService.getTasksByStatus(user, "PENDING"));
            model.addAttribute("completedTasks", taskService.getTasksByStatus(user, "COMPLETED"));
        } catch (Exception e) {
            model.addAttribute("goals", new ArrayList<>());
            model.addAttribute("tasks", new ArrayList<>());
            model.addAttribute("pendingTasks", new ArrayList<>());
            model.addAttribute("completedTasks", new ArrayList<>());
        }
        return "dashboard";
    }
}
