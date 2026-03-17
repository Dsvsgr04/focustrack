package focustrack.controller;

import focustrack.entity.Challenge;
import focustrack.entity.User;
import focustrack.service.ChallengeService;
import focustrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    // SINGLE @GetMapping method with debug prints
    @GetMapping
    public String challengesPage(Model model, Principal principal) {
        System.out.println(">>> Entering challenges page controller");

        try {
            if (principal == null || principal.getName() == null) {
                System.out.println(">>> No authenticated user found");
                return "redirect:/login";
            }

            User user = userService.findByEmail(principal.getName());
            System.out.println(">>> User found: " + user.getEmail());

            model.addAttribute("challenges", challengeService.getUserChallenges(user));
            model.addAttribute("newChallenge", new Challenge());

            System.out.println(">>> Returning template 'challenges'");
            return "challenges";

        } catch (Exception e) {
            System.out.println(">>> Error in challenges page: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error loading challenges: " + e.getMessage());
            return "error";  // make sure you have templates/error.html
        }
    }

    @PostMapping("/create")
    public String createChallenge(@ModelAttribute Challenge challenge, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }

            User user = userService.findByEmail(principal.getName());
            challenge.setUser(user);
            challengeService.createChallenge(challenge);

            System.out.println(">>> Challenge created: " + challenge.getTitle());
            return "redirect:/challenges";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/challenges?error=true";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteChallenge(@PathVariable Long id) {
        try {
            challengeService.deleteChallenge(id);
            System.out.println(">>> Challenge deleted: ID " + id);
            return "redirect:/challenges";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/challenges?error=deleteFailed";
        }
    }

    // Optional: simple progress/status update
    @PostMapping("/update/{id}")
    public String updateChallenge(@PathVariable Long id,
                                  @RequestParam int progress,
                                  @RequestParam String status) {
        try {
            Challenge challenge = challengeService.getChallengeById(id);
            challenge.setProgress(Math.max(0, Math.min(100, progress))); // clamp 0–100
            challenge.setStatus(status);
            challengeService.updateChallenge(challenge);
            System.out.println(">>> Challenge updated: ID " + id + ", progress=" + progress);
            return "redirect:/challenges";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/challenges?error=updateFailed";
        }
    }
}