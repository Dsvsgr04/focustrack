package focustrack.controller;

import focustrack.entity.Goal;
import focustrack.entity.User;
import focustrack.service.GoalService;
import focustrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String goals(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("goals", goalService.getUserGoals(user));
        model.addAttribute("newGoal", new Goal());
        return "goals";
    }

    @PostMapping("/create")
    public String createGoal(@ModelAttribute Goal goal, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        goal.setUser(user);
        goalService.createGoal(goal);
        return "redirect:/goals";
    }

    @GetMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "redirect:/goals";
    }

    @PostMapping("/update/{id}")
    public String updateGoal(@PathVariable Long id,
                             @RequestParam int progress,
                             @RequestParam String status) {
        Goal goal = goalService.getGoalById(id);
        goal.setProgress(progress);
        goal.setStatus(status);
        goalService.updateGoal(goal);
        return "redirect:/goals";
    }
}