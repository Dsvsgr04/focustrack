package focustrack.controller;

import focustrack.entity.User;
import focustrack.service.GoalService;
import focustrack.service.TaskService;
import focustrack.service.UserService;
import focustrack.service.ChallengeService;   // ← ADD THIS IMPORT

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

    @Autowired
    private ChallengeService challengeService;   // ← ADD THIS LINE

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        try {
            User user = userService.findByEmail(principal.getName());

            model.addAttribute("user", user);
            model.addAttribute("goals", goalService.getUserGoals(user));
            model.addAttribute("completedGoals", goalService.getUserGoalsByStatus(user, "COMPLETED"));
            model.addAttribute("activeGoals", goalService.getUserGoalsByStatus(user, "ACTIVE"));
            model.addAttribute("tasks", taskService.getUserTasks(user));
            model.addAttribute("pendingTasks", taskService.getTasksByStatus(user, "PENDING"));
            model.addAttribute("completedTasks", taskService.getTasksByStatus(user, "COMPLETED"));

            // ADD THESE TWO LINES (or adjust as you prefer)
            model.addAttribute("challenges", challengeService.getUserChallenges(user));
            // or: challengeService.getUserChallengesByStatus(user, "ACTIVE");

        } catch (Exception e) {
            // ... your existing catch block ...

            // Also add fallback for challenges to avoid null/Thymeleaf errors
            model.addAttribute("challenges", new ArrayList<>());
        }
        return "dashboard";
    }
}